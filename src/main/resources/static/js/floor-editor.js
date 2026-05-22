const container = document.getElementById('plan-container');

let isDrawing = false;
let startX = 0;
let startY = 0;
let currentBox = null;
let selection = null;
let mode = 'draw';

function setMode(newMode) {
    mode = newMode;
    const panel = document.getElementById('edit-panel');
    panel.style.display = newMode === 'edit' ? 'block' : 'none';

    if (currentBox) {
        currentBox.remove();
        currentBox = null;
    }

    selection = null;
    isDrawing = false;
}

function onOfficeClick(el, event) {
    event.stopPropagation();

    const officeId = el.dataset.id;

    if (mode === 'edit') {
        loadOfficeToEdit(officeId);
    } else if (mode === 'delete') {
        if (confirm('Delete this office from plan and database?')) {
            deleteOfficeById(officeId);
        }
    }
}

container.addEventListener('mousedown', (e) => {
    if (mode !== 'draw') {
        return;
    }

    if (e.target.closest('.office-box')) {
        return;
    }

    isDrawing = true;

    const rect = container.getBoundingClientRect();
    startX = e.clientX - rect.left;
    startY = e.clientY - rect.top;

    if (currentBox) {
        currentBox.remove();
    }

    currentBox = document.createElement('div');
    currentBox.className = 'selection-box';
    currentBox.style.left = startX + 'px';
    currentBox.style.top = startY + 'px';

    container.appendChild(currentBox);
});

container.addEventListener('mousemove', (e) => {
    if (mode !== 'draw' || !isDrawing || !currentBox) {
        return;
    }

    const rect = container.getBoundingClientRect();
    const currentX = e.clientX - rect.left;
    const currentY = e.clientY - rect.top;

    const width = currentX - startX;
    const height = currentY - startY;

    const left = width < 0 ? currentX : startX;
    const top = height < 0 ? currentY : startY;

    currentBox.style.left = left + 'px';
    currentBox.style.top = top + 'px';
    currentBox.style.width = Math.abs(width) + 'px';
    currentBox.style.height = Math.abs(height) + 'px';

    selection = {
        x: left,
        y: top,
        width: Math.abs(width),
        height: Math.abs(height)
    };
});

window.addEventListener('mouseup', () => {
    isDrawing = false;
});

function saveSelection() {
    if (!selection) {
        setStatus('Create selection first', 'red');
        return;
    }

    const officeId = document.getElementById('office-select').value;

    fetch('/admin/floor-plan/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            officeId: officeId,
            floorNumber: 1,
            x: selection.x,
            y: selection.y,
            width: selection.width,
            height: selection.height
        })
    })
        .then(response => response.text())
        .then(() => {
            setStatus('Changes saved', 'green');
            location.reload();
        })
        .catch(() => {
            setStatus('Save error', 'red');
        });
}

function createOffice() {
    const number = document.getElementById('new-number').value;
    const area = document.getElementById('new-area').value;
    const floor = document.getElementById('new-floor').value;
    const capacity = document.getElementById('new-capacity').value;
    const officeType = document.getElementById('new-type').value;
    const hasFurniture = document.getElementById('new-furniture').checked;
    const price = document.getElementById('new-price').value;
    const status = document.getElementById('new-status').value;
    const buildingId = document.getElementById('new-building').value;

    fetch('/admin/offices/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            number: number,
            area: area,
            floor: floor,
            capacity: capacity,
            officeType: officeType,
            hasFurniture: hasFurniture,
            rentalPrice: price,
            status: status,
            buildingId: buildingId
        })
    })
        .then(response => response.json())
        .then(office => {
            const select = document.getElementById('office-select');
            const option = document.createElement('option');
            option.value = office.id;
            option.text = office.number;
            option.selected = true;
            select.appendChild(option);
            setStatus('Office created', 'green');
            location.reload();
        })
        .catch(() => {
            setStatus('Create error', 'red');
        });
}

function loadOfficeToEdit(officeId) {
    fetch('/admin/offices/' + officeId)
        .then(response => response.json())
        .then(office => {
            document.getElementById('edit-office-id').value = office.id;
            document.getElementById('edit-number').value = office.number;
            document.getElementById('edit-area').value = office.area;
            document.getElementById('edit-floor').value = office.floor;
            document.getElementById('edit-capacity').value = office.capacity;
            document.getElementById('edit-type').value = office.officeType;
            document.getElementById('edit-furniture').checked = office.hasFurniture;
            document.getElementById('edit-price').value = office.rentalPrice;
            document.getElementById('edit-status').value = office.status;
            document.getElementById('edit-panel').style.display = 'block';
        });
}

function updateOffice() {
    const officeId = document.getElementById('edit-office-id').value;

    fetch('/admin/offices/update/' + officeId, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            number: document.getElementById('edit-number').value,
            area: document.getElementById('edit-area').value,
            floor: document.getElementById('edit-floor').value,
            capacity: document.getElementById('edit-capacity').value,
            officeType: document.getElementById('edit-type').value,
            hasFurniture: document.getElementById('edit-furniture').checked,
            rentalPrice: document.getElementById('edit-price').value,
            status: document.getElementById('edit-status').value
        })
    })
        .then(response => response.json())
        .then(() => {
            setStatus('Office updated', 'green');
            location.reload();
        })
        .catch(() => {
            setStatus('Update error', 'red');
        });
}

function deleteOffice() {
    const officeId = document.getElementById('edit-office-id').value;
    deleteOfficeById(officeId);
}

function deleteOfficeById(officeId) {
    fetch('/admin/offices/delete/' + officeId, {
        method: 'DELETE'
    })
        .then(response => response.text())
        .then(() => {
            setStatus('Office deleted', 'green');
            location.reload();
        })
        .catch(() => {
            setStatus('Delete error', 'red');
        });
}

function setStatus(text, color) {
    const status = document.getElementById('save-status');
    status.innerText = text;
    status.style.color = color;
}