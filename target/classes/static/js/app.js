/**
 * UAM Hub - Operational Client Scripts
 * Handles Live UTC Clock, Functional Radar Canvas, Async Actions via Fetch, and Modals
 */

const API_BASE = '/api';

document.addEventListener('DOMContentLoaded', () => {
    initClock();
    initRadarCanvas();
    setupEscapeKeyModalClose();
});

// ==========================================================================
// 1. Live UTC Clock & Date
// ==========================================================================
function initClock() {
    const clockEl = document.getElementById('live-clock');
    const dateEl = document.getElementById('live-date');
    if (!clockEl && !dateEl) return;

    function update() {
        const now = new Date();
        const hours = String(now.getUTCHours()).padStart(2, '0');
        const minutes = String(now.getUTCMinutes()).padStart(2, '0');
        const seconds = String(now.getUTCSeconds()).padStart(2, '0');
        
        if (clockEl) {
            clockEl.textContent = `${hours}:${minutes}:${seconds} UTC`;
        }

        if (dateEl) {
            const options = { weekday: 'short', year: 'numeric', month: 'short', day: '2-digit', timeZone: 'UTC' };
            dateEl.textContent = now.toLocaleDateString('en-US', options);
        }
    }

    update();
    setInterval(update, 1000);
}

// ==========================================================================
// 2. Functional Radar Visualizer (Light Enterprise Topographic Theme)
// ==========================================================================
let radarCanvas = null;
let radarCtx = null;
let radarSweepAngle = 0;
let radarAnimationId = null;
let cachedZones = [];

function initRadarCanvas() {
    radarCanvas = document.getElementById('radar-canvas');
    if (!radarCanvas) return;
    radarCtx = radarCanvas.getContext('2d');

    // Load zones from API for real plotting
    fetch(`${API_BASE}/airspace-zones`)
        .then(res => res.json())
        .then(zones => {
            cachedZones = zones;
            startRadarLoop();
        })
        .catch(err => {
            console.error('Failed to load zones for radar visualizer:', err);
            startRadarLoop();
        });
}

function startRadarLoop() {
    if (!radarCanvas || !radarCtx) return;

    function render() {
        const width = radarCanvas.width;
        const height = radarCanvas.height;
        const cx = width / 2;
        const cy = height / 2;

        radarCtx.fillStyle = '#0B1E33';
        radarCtx.fillRect(0, 0, width, height);

        radarCtx.strokeStyle = 'rgba(0, 194, 203, 0.24)';
        radarCtx.lineWidth = 1;
        const rings = [35, 70, 105, 140];
        const ringLabels = ['5km', '10km', '15km', '20km'];

        rings.forEach((radius, idx) => {
            radarCtx.beginPath();
            radarCtx.arc(cx, cy, radius, 0, Math.PI * 2);
            radarCtx.stroke();

            radarCtx.fillStyle = 'rgba(232, 236, 241, 0.7)';
            radarCtx.font = '9px Inter, sans-serif';
            radarCtx.fillText(ringLabels[idx], cx + 4, cy - radius + 11);
        });

        radarCtx.strokeStyle = 'rgba(0, 194, 203, 0.18)';
        radarCtx.beginPath();
        radarCtx.moveTo(cx, 10);
        radarCtx.lineTo(cx, height - 10);
        radarCtx.moveTo(10, cy);
        radarCtx.lineTo(width - 10, cy);
        radarCtx.stroke();

radarCtx.strokeStyle = 'rgba(0, 194, 203, 0.1)';
        radarCtx.beginPath();
        radarCtx.moveTo(cx - 100, cy - 100);
        radarCtx.lineTo(cx + 100, cy + 100);
        radarCtx.moveTo(cx - 100, cy + 100);
        radarCtx.lineTo(cx + 100, cy - 100);
        radarCtx.stroke();

        cachedZones.forEach((zone, index) => {
            const angle = (index + 1) * (Math.PI / 2.2);
            const radius = 55 + (index * 32) % 95;

            const x = cx + Math.cos(angle) * radius;
            const y = cy + Math.sin(angle) * radius;

            let color = '#2ECC71';
            if (zone.zoneType === 'CAUTION') color = '#FF9F1C';
            if (zone.zoneType === 'RESTRICTED' || zone.zoneType === 'NO_FLY') color = '#E63946';

            radarCtx.beginPath();
            radarCtx.arc(x, y, 7, 0, Math.PI * 2);
            radarCtx.strokeStyle = color;
            radarCtx.lineWidth = 1.2;
            radarCtx.shadowBlur = 12;
            radarCtx.shadowColor = color;
            radarCtx.stroke();

            radarCtx.beginPath();
            radarCtx.arc(x, y, 3.5, 0, Math.PI * 2);
            radarCtx.fillStyle = color;
            radarCtx.fill();
            radarCtx.shadowBlur = 0;

            radarCtx.fillStyle = '#E8ECF1';
            radarCtx.font = '10px JetBrains Mono, monospace';
            radarCtx.fillText(zone.zoneCode, x + 10, y + 4);
        });

        // Subtle sweep beam
        radarSweepAngle = (radarSweepAngle + 0.02) % (Math.PI * 2);
        radarCtx.save();
        radarCtx.translate(cx, cy);
        radarCtx.rotate(radarSweepAngle);

        const sweepGrad = radarCtx.createLinearGradient(0, 0, 140, 0);
        sweepGrad.addColorStop(0, 'rgba(0, 194, 203, 0.34)');
        sweepGrad.addColorStop(1, 'rgba(0, 194, 203, 0.01)');

        radarCtx.beginPath();
        radarCtx.moveTo(0, 0);
        radarCtx.arc(0, 0, 140, 0, 0.35);
        radarCtx.closePath();
        radarCtx.fillStyle = sweepGrad;
        radarCtx.fill();

        radarCtx.restore();

        radarAnimationId = requestAnimationFrame(render);
    }

    render();
}

// ==========================================================================
// 3. Modal Helpers
// ==========================================================================
function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add('active');
        const firstInput = modal.querySelector('input, select');
        if (firstInput) firstInput.focus();
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove('active');
    }
}

function setupEscapeKeyModalClose() {
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') {
            document.querySelectorAll('.modal-overlay.active').forEach(modal => {
                modal.classList.remove('active');
            });
        }
    });

    document.querySelectorAll('.modal-overlay').forEach(overlay => {
        overlay.addEventListener('click', (e) => {
            if (e.target === overlay) {
                overlay.classList.remove('active');
            }
        });
    });
}

// ==========================================================================
// 4. Finance & Reports Underline-Tab Switcher
// ==========================================================================
function switchFinanceSubTab(subTabId) {
    document.querySelectorAll('.sub-tab-btn').forEach(btn => btn.classList.remove('active'));
    document.querySelectorAll('.sub-tab-content').forEach(content => content.classList.remove('active'));

    const activeBtn = event ? event.currentTarget : document.querySelector(`[onclick*="${subTabId}"]`);
    if (activeBtn) activeBtn.classList.add('active');

    const targetContent = document.getElementById(subTabId);
    if (targetContent) targetContent.classList.add('active');

    // Update URL query string without reloading page
    const tabName = subTabId.replace('fin-', '');
    const newUrl = `${window.location.pathname}?tab=${tabName}`;
    window.history.pushState({ path: newUrl }, '', newUrl);
}

function switchReportTab(reportId) {
    document.querySelectorAll('.report-tab-btn').forEach(btn => btn.classList.remove('active'));
    const activeBtn = event ? event.currentTarget : document.querySelector(`[onclick*="${reportId}"]`);
    if (activeBtn) activeBtn.classList.add('active');

    const balanceSec = document.getElementById('report-balance-sheet');
    const pnlSec = document.getElementById('report-profit-loss');
    const budgetSec = document.getElementById('report-budget-variance');

    if (reportId === 'all') {
        if (balanceSec) balanceSec.style.display = 'block';
        if (pnlSec) pnlSec.style.display = 'block';
        if (budgetSec) budgetSec.style.display = 'block';
    } else if (reportId === 'balance') {
        if (balanceSec) balanceSec.style.display = 'block';
        if (pnlSec) pnlSec.style.display = 'none';
        if (budgetSec) budgetSec.style.display = 'none';
    } else if (reportId === 'pnl') {
        if (balanceSec) balanceSec.style.display = 'none';
        if (pnlSec) pnlSec.style.display = 'block';
        if (budgetSec) budgetSec.style.display = 'none';
    } else if (reportId === 'budget') {
        if (balanceSec) balanceSec.style.display = 'none';
        if (pnlSec) pnlSec.style.display = 'none';
        if (budgetSec) budgetSec.style.display = 'block';
    }
}

// ==========================================================================
// 5. Asynchronous Actions via Fetch API
// ==========================================================================

// Add Landing Pad
async function handleAddPad(e) {
    e.preventDefault();
    const payload = {
        padCode: document.getElementById('padCode').value.trim(),
        locationName: document.getElementById('locationName').value.trim(),
        latitude: parseFloat(document.getElementById('latitude').value),
        longitude: parseFloat(document.getElementById('longitude').value),
        maxWeightCapacity: parseFloat(document.getElementById('maxWeightCapacity').value),
        status: 'AVAILABLE'
    };

    try {
        const res = await fetch(`${API_BASE}/landing-pads`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            closeModal('add-pad-modal');
            window.location.reload();
        } else {
            const err = await res.json().catch(() => ({}));
            alert('Failed to create landing pad: ' + (err.message || res.statusText));
        }
    } catch (err) {
        alert('Network error while creating landing pad.');
    }
}

// Delete Landing Pad
async function deletePad(id) {
    if (!confirm(`Are you sure you want to delete Landing Pad #${id}?`)) return;

    try {
        const res = await fetch(`${API_BASE}/landing-pads/${id}`, { method: 'DELETE' });
        if (res.ok) {
            const row = document.getElementById(`pad-row-${id}`);
            if (row) row.remove();
            else window.location.reload();
        } else {
            alert('Error deleting landing pad.');
        }
    } catch (err) {
        alert('Network error while deleting landing pad.');
    }
}

// Register Drone
async function handleAddDrone(e) {
    e.preventDefault();
    const operatorId = document.getElementById('operatorId').value;
    const payload = {
        model: document.getElementById('droneModel').value.trim(),
        registrationNumber: document.getElementById('regNumber').value.trim(),
        batteryCapacity: parseInt(document.getElementById('batteryCapacity').value, 10),
        currentBatteryLevel: 100,
        payloadCapacity: parseFloat(document.getElementById('payloadCapacity').value),
        status: 'AVAILABLE',
        operator: operatorId ? { id: parseInt(operatorId, 10) } : null
    };

    try {
        const res = await fetch(`${API_BASE}/drones`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            closeModal('add-drone-modal');
            window.location.reload();
        } else {
            const err = await res.json().catch(() => ({}));
            alert('Failed to register drone: ' + (err.message || res.statusText));
        }
    } catch (err) {
        alert('Network error while registering drone.');
    }
}

// Delete Drone
async function deleteDrone(id) {
    if (!confirm(`Are you sure you want to delete Aircraft #${id}?`)) return;

    try {
        const res = await fetch(`${API_BASE}/drones/${id}`, { method: 'DELETE' });
        if (res.ok) {
            const row = document.getElementById(`drone-row-${id}`);
            if (row) row.remove();
            else window.location.reload();
        } else {
            alert('Error deleting aircraft.');
        }
    } catch (err) {
        alert('Network error while deleting aircraft.');
    }
}

// Add Airspace Zone / Corridor
async function handleAddZone(e) {
    e.preventDefault();
    const payload = {
        zoneCode: document.getElementById('zoneCode').value.trim(),
        name: document.getElementById('zoneName').value.trim(),
        zoneType: document.getElementById('zoneType').value,
        minAltitude: parseFloat(document.getElementById('minAltitude').value),
        maxAltitude: parseFloat(document.getElementById('maxAltitude').value),
        isActive: true
    };

    try {
        const res = await fetch(`${API_BASE}/airspace-zones`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            closeModal('add-zone-modal');
            window.location.reload();
        } else {
            alert('Failed to add corridor.');
        }
    } catch (err) {
        alert('Network error while adding corridor.');
    }
}

// Delete Airspace Zone
async function deleteZone(id) {
    if (!confirm(`Are you sure you want to remove Airway Corridor #${id}?`)) return;

    try {
        const res = await fetch(`${API_BASE}/airspace-zones/${id}`, { method: 'DELETE' });
        if (res.ok) {
            const row = document.getElementById(`zone-row-${id}`);
            if (row) row.remove();
            else window.location.reload();
        } else {
            alert('Error deleting corridor.');
        }
    } catch (err) {
        alert('Network error while deleting corridor.');
    }
}

// Express Check-In
async function handleCheckIn(e) {
    e.preventDefault();
    const droneId = document.getElementById('checkin-drone').value;
    const padId = document.getElementById('checkin-pad').value;

    if (!droneId || !padId) {
        alert('Please select both a drone and a landing pad.');
        return;
    }

    try {
        const res = await fetch(`${API_BASE}/docking-transactions/check-in?droneId=${droneId}&landingPadId=${padId}`, {
            method: 'POST'
        });

        if (res.ok) {
            closeModal('checkin-modal');
            window.location.reload();
        } else {
            alert('Error performing check-in. The pad or drone may already be occupied.');
        }
    } catch (err) {
        alert('Network error during express check-in.');
    }
}

// Check-Out
async function checkOutTransaction(id) {
    if (!confirm('Confirm departure & check-out for this docking transaction?')) return;

    try {
        const res = await fetch(`${API_BASE}/docking-transactions/${id}/check-out`, {
            method: 'PUT'
        });

        if (res.ok) {
            window.location.reload();
        } else {
            alert('Failed to check out docking transaction.');
        }
    } catch (err) {
        alert('Network error during check-out.');
    }
}
