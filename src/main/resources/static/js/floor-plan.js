const planContainer = document.querySelector('.plan-container');
const infoPanel = document.querySelector('.info-panel');
const filtersPanel = document.getElementById('filters-panel');
const filtersToggleBtn = document.getElementById('filters-toggle-btn');

filtersToggleBtn.addEventListener('click', () => {
    const isOpen = filtersPanel.style.display === 'block';
    filtersPanel.style.display = isOpen ? 'none' : 'block';
    filtersToggleBtn.innerText = isOpen ? 'Использовать фильтры' : 'Скрыть фильтры';
});

function showOfficeInfo(element, event) {
    if (event) event.stopPropagation();

    document.getElementById('office-id-input').value = element.dataset.id;
    document.getElementById('office-number').innerText = element.dataset.number;
    document.getElementById('office-area').innerText = element.dataset.area;
    document.getElementById('office-price').innerText = element.dataset.price;
    document.getElementById('office-floor').innerText = element.dataset.floor;
    document.getElementById('office-type').innerText = element.dataset.type;

    infoPanel.style.display = 'block';
}

function hideOfficeInfo() {
    infoPanel.style.display = 'none';
    document.getElementById('office-id-input').value = '';
    document.getElementById('office-number').innerText = '-';
    document.getElementById('office-area').innerText = '-';
    document.getElementById('office-price').innerText = '-';
    document.getElementById('office-floor').innerText = '-';
    document.getElementById('office-type').innerText = '-';
}

planContainer.addEventListener('click', (e) => {
    if (!e.target.closest('.office-box')) {
        hideOfficeInfo();
    }
});