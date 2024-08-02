const companySearchContainer = document.getElementById("company-search-row");
const companySearchInput = document.getElementById("company-search-input");
const companyResultBox = document.getElementById("company-result-box");
const companySearchList = document.getElementById("company-result-list");
let companyTimer;
let dataSuggestions = [];

companySearchInput.addEventListener("click", ()=> {
    companySearchInput.value = "";
})

companySearchInput.addEventListener('click', () => {
    clearCompanySearch();
});

addEventListener('resize', () => {
    clearCompanySearch();
});
addEventListener('load', () => {
    clearCompanySearch();
});
function clearCompanySearch(){
    companySearchInput.value = '';
    dataSuggestions = [];
    companyResultBox.classList.add("company-result-box-hide")
}


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

    function highlightMatch(text, query) {
        const regex = new RegExp(`(${query})`, 'gi');
        return text.replace(regex, '<span class="search-color">$1</span>');
    }

    filteredSuggestions.forEach(suggestion => {
        let listLink = document.createElement('a');
        let companyName = suggestion.name.replaceAll(' ', '-');
        listLink.href = "/katalog-firm/firma/" + companyName + "/" + suggestion.id
        listLink.innerHTML = highlightMatch(suggestion.name,input);
        companySearchList.appendChild(listLink);
    });
}