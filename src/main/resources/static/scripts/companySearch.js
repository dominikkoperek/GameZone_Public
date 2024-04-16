const companySearchInput = document.getElementById("company-search-input");
const companySearchList = document.getElementById("company-result-list");
const companyResultBox = document.getElementById("company-result-box");
const companySearchContainer = document.getElementById("company-search-row");
let companyTimer;
let dataSuggestions = [];


companySearchInput.onkeyup = (e) => {
    let userData = e.target.value;
    clearTimeout(companyTimer);
    companyTimer = setTimeout(function () {
        if (companyResultBox.classList.contains("company-result-box-hide")) {
            companyResultBox.classList.remove("company-result-box-hide")
        }
        if (userData.length < 1) {
            companyResultBox.classList.add("company-result-box-hide")
        }
        getCompanyName(userData)
    }, 450)

}
//ADD EVENT LISTENER IF COMPANY RESULT BOX CONTAINS HIDE
companySearchInput.onchange = (e) => {
    if (!companyResultBox.classList.contains("company-result-box-hide")) {
        document.addEventListener('click', function (ev) {
            if (!companySearchContainer.contains(ev.target)) {
                companySearchInput.value = '';
                companySearchList.innerHTML = '';
                companyResultBox.classList.add("company-result-box-hide")
            } else if (companySearchInput.value !== '') {
                companySearchInput.value = '';
                companySearchList.innerHTML = '';
                companyResultBox.classList.add("company-result-box-hide")
            }
        });
    }
}


function getCompanyName(input) {
    if (dataSuggestions.length>1) {
        showCompany(input);
    } else {
        $.ajax({
            url: '/api/company/allCompanies',
            type: 'GET',
            success: function (data) {
                dataSuggestions = data;
                showCompany(input);
            },
            error: function (error) {
                console.error('Error: ', error);
            }
        });
    }
}

function showCompany(input) {
    let filteredSuggestions = dataSuggestions.filter(suggestion => suggestion.name.toLowerCase().trim()
        .includes(input.trim().toLowerCase()));
    filteredSuggestions = filteredSuggestions.sort((a, b) => a.name.localeCompare(b.name)).slice(0, 6);
    if (!input.trim()) {
        companySearchList.innerHTML = '';
        return;
    }
    if (filteredSuggestions.length < 1) {
        companyResultBox.classList.add("company-result-box-hide")
    }
    companySearchList.innerHTML = '';
    filteredSuggestions.forEach(suggestion => {
        let listLink = document.createElement('a');
        let companyName = suggestion.name.replaceAll(' ', '-');
        listLink.href = "/katalog-firm/firma/" + companyName + "/" + suggestion.id
        listLink.textContent = suggestion.name;
        companySearchList.appendChild(listLink);
    });
}