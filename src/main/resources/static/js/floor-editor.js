const container = document.getElementById('plan-container');

let mode = 'draw';

let isDrawing = false;

let startX = 0;
let startY = 0;

let currentBox = null;

let selection = null;

function setMode(newMode) {

    mode = newMode;

    document.getElementById('draw-toolbar').style.display = newMode === 'draw' ? 'flex' : 'none';
    document.getElementById('edit-toolbar').style.display = newMode === 'edit' ? 'flex' : 'none';

    clearSelection();
}

function clearSelection() {

    if (currentBox) {
        currentBox.remove();
        currentBox = null;
    }

    selection = null;
    isDrawing = false;
}

function onOfficeClick(element, event) {

    event.stopPropagation();

    const officeId = element.dataset.id;

    if (mode === 'edit') {
        loadOfficeToEdit(officeId);
    }

    if (mode === 'delete') {

        const confirmed = confirm('Вы уверены, что хотите удалить это помещение ?');

        if (confirmed) {
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

    clearSelection();

    isDrawing = true;

    const rect = container.getBoundingClientRect();

    startX = e.clientX - rect.left;
    startY = e.clientY - rect.top;

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

async function createOffice() {

    try {

        const response = await fetch('/admin/offices/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    number:
                    document.getElementById('new-number').value,

                    area:
                    document.getElementById('new-area').value,

                    floor:
                    document.getElementById('new-floor').value,

                    capacity:
                    document.getElementById('new-capacity').value,

                    officeType:
                    document.getElementById('new-type').value,

                    hasFurniture:
                    document.getElementById('new-furniture').checked,

                    rentalPrice:
                    document.getElementById('new-price').value,

                    status:
                    document.getElementById('new-status').value,

                    buildingId:
                    document.getElementById('new-building').value
                })
            });

        if (!response.ok) {
            throw new Error();
        }

        const office = await response.json();
        const select = document.getElementById('office-select');
        const option = document.createElement('option');

        option.value = office.id;
        option.text = office.number;
        option.selected = true;

        select.appendChild(option);

        setStatus('Помещение создано. Нарисуйте его на схеме и сохраните.', 'green');

    } catch (e) {

        console.error(e);
        setStatus('Ошибка создания', 'red');
    }
}

async function saveSelection() {

    if (!selection) {
        setStatus('Нарисуйте область', 'red');

        return;
    }

    const officeId = document.getElementById('office-select').value;

    if (!officeId) {
        setStatus('Выберите помещение', 'red');

        return;
    }

    try {

        const response = await fetch('/admin/floor-plan/save', {
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
            });

        if (!response.ok) {
            throw new Error();
        }

        setStatus('Расположение сохранено', 'green');

        location.reload();

    } catch (e) {

        console.error(e);
        setStatus('Ошибка сохранения', 'red');
    }
}

async function loadOfficeToEdit(officeId) {

    const response = await fetch('/admin/offices/' + officeId);
    const office = await response.json();

    document.getElementById('edit-office-id').value = office.id;
    document.getElementById('edit-number').value = office.number;
    document.getElementById('edit-area').value = office.area;
    document.getElementById('edit-floor').value = office.floor;
    document.getElementById('edit-capacity').value = office.capacity;
    document.getElementById('edit-price').value = office.rentalPrice;
    document.getElementById('edit-type').value = office.officeType;
    document.getElementById('edit-furniture').checked = office.hasFurniture;
    document.getElementById('edit-status').value = office.status;
}

async function updateOffice() {

    const officeId = document.getElementById('edit-office-id').value;

    try {

        const response = await fetch('/admin/offices/update/' + officeId, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    number:
                    document.getElementById('edit-number').value,

                    area:
                    document.getElementById('edit-area').value,

                    floor:
                    document.getElementById('edit-floor').value,

                    capacity:
                    document.getElementById('edit-capacity').value,

                    officeType:
                    document.getElementById('edit-type').value,

                    hasFurniture:
                    document.getElementById('edit-furniture').checked,

                    rentalPrice:
                    document.getElementById('edit-price').value,

                    status:
                    document.getElementById('edit-status').value
                })
            });

        if (!response.ok) {
            throw new Error();
        }

        setStatus('Информация о помещении изменена', 'green');

        location.reload();

    } catch (e) {

        console.error(e);
        setStatus('Ошибка обновления информации о помещении', 'red');
    }
}

function deleteOffice() {

    const officeId = document.getElementById('edit-office-id').value;

    deleteOfficeById(officeId);

}

async function deleteOfficeById(officeId) {

    try {

        const response = await fetch('/admin/offices/delete/' + officeId, {
                method: 'DELETE'
            });

        if (!response.ok) {
            throw new Error();
        }

        setStatus('Офис удален', 'green');

        location.reload();

    } catch (e) {

        console.error(e);
        setStatus('Ошибка удаления', 'red');
    }
}

function setStatus(text, color) {

    const status = document.getElementById('save-status');

    status.innerText = text;
    status.style.color = color;
}

setMode('draw');