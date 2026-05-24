document.addEventListener('DOMContentLoaded', () => {
    const planContainer = document.querySelector('.plan-container');
    const infoPanel = document.querySelector('.info-panel');
    const filtersPanel = document.getElementById('filters-panel');
    const filtersToggleBtn = document.getElementById('filters-toggle-btn');

    if (filtersToggleBtn && filtersPanel) {
        filtersToggleBtn.addEventListener('click', () => {
            const isOpen = filtersPanel.style.display === 'block';
            filtersPanel.style.display = isOpen ? 'none' : 'block';
            filtersToggleBtn.innerText = isOpen ? 'Использовать фильтры' : 'Скрыть фильтры';
        });
    }

    function hideOfficeInfo() {
        if (!infoPanel) return;

        infoPanel.style.display = 'none';

        const officeIdInput = document.getElementById('office-id-input');
        if (officeIdInput) officeIdInput.value = '';

        const officeNumber = document.getElementById('office-number');
        const officeArea = document.getElementById('office-area');
        const officePrice = document.getElementById('office-price');
        const officeFloor = document.getElementById('office-floor');
        const officeType = document.getElementById('office-type');

        if (officeNumber) officeNumber.innerText = '-';
        if (officeArea) officeArea.innerText = '-';
        if (officePrice) officePrice.innerText = '-';
        if (officeFloor) officeFloor.innerText = '-';
        if (officeType) officeType.innerText = '-';
    }

    window.showOfficeInfo = function(element, event) {
        const officeTypeMap = {
            STANDARD: 'Открытый офис',
            MEETING_ROOM: 'Переговорная',
            REST_ZONE: 'Зона отдыха',
            SERVER_ROOM: 'Серверная',
            WAREHOUSE: 'Склад'
        };

        if (event) event.stopPropagation();

        const officeIdInput = document.getElementById('office-id-input');
        const officeNumber = document.getElementById('office-number');
        const officeArea = document.getElementById('office-area');
        const officePrice = document.getElementById('office-price');
        const officeFloor = document.getElementById('office-floor');
        const officeType = document.getElementById('office-type');

        if (officeIdInput) officeIdInput.value = element.dataset.id;
        if (officeNumber) officeNumber.innerText = element.dataset.number;
        if (officeArea) officeArea.innerText = element.dataset.area;
        if (officePrice) officePrice.innerText = element.dataset.price;
        if (officeFloor) officeFloor.innerText = element.dataset.floor;
        if (officeType) officeType.innerText = officeTypeMap[element.dataset.type] || element.dataset.type;

        if (infoPanel) infoPanel.style.display = 'block';
    };

    window.hideOfficeInfo = hideOfficeInfo;

    if (planContainer) {
        planContainer.addEventListener('click', (e) => {
            if (!e.target.closest('.office-box')) {
                hideOfficeInfo();
            }
        });
    }
});