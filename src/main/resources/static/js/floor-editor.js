const container =
    document.getElementById('plan-container');

let isDrawing = false;

let startX = 0;
let startY = 0;

let currentBox = null;

let selection = null;

container.addEventListener('mousedown', (e) => {

    isDrawing = true;

    const rect =
        container.getBoundingClientRect();

    startX = e.clientX - rect.left;
    startY = e.clientY - rect.top;

    if (currentBox) {
        currentBox.remove();
    }

    currentBox =
        document.createElement('div');

    currentBox.className =
        'selection-box';

    currentBox.style.left =
        startX + 'px';

    currentBox.style.top =
        startY + 'px';

    container.appendChild(currentBox);

});

container.addEventListener('mousemove', (e) => {

    if (!isDrawing || !currentBox) {
        return;
    }

    const rect =
        container.getBoundingClientRect();

    const currentX =
        e.clientX - rect.left;

    const currentY =
        e.clientY - rect.top;

    const width =
        currentX - startX;

    const height =
        currentY - startY;

    const left =
        width < 0 ? currentX : startX;

    const top =
        height < 0 ? currentY : startY;

    currentBox.style.left =
        left + 'px';

    currentBox.style.top =
        top + 'px';

    currentBox.style.width =
        Math.abs(width) + 'px';

    currentBox.style.height =
        Math.abs(height) + 'px';

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

    const officeId =
        document.getElementById('office-select').value;

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

        .then(data => {

            setStatus('Changes saved', 'green');

        })

        .catch(error => {

            setStatus('Save error', 'red');

        });
}

function createOffice() {

    const number =
        document.getElementById('new-number').value;

    const area =
        document.getElementById('new-area').value;

    const floor =
        document.getElementById('new-floor').value;

    const price =
        document.getElementById('new-price').value;

    fetch('/admin/offices/create', {

        method: 'POST',

        headers: {
            'Content-Type': 'application/json'
        },

        body: JSON.stringify({

            number: number,
            area: area,
            floor: floor,
            rentalPrice: price

        })

    })
        .then(response => response.json())

        .then(office => {

            const select =
                document.getElementById('office-select');

            const option =
                document.createElement('option');

            option.value = office.id;

            option.text =
                office.number;

            option.selected = true;

            select.appendChild(option);

            setStatus(
                'Office created',
                'green'
            );

        })

        .catch(error => {

            setStatus(
                'Create error',
                'red'
            );

        });
}

function setStatus(text, color) {

    const status =
        document.getElementById('save-status');

    status.innerText = text;

    status.style.color = color;
}