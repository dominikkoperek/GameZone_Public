// SHOW TITLE SUGGESTION

let gameTitle = document.getElementById("game-title");
let gameListSuggestions = document.getElementById("show-title-suggestion-list");
let timerGameTitleSuggestions;
let gameTitleHintsResult = [];

gameTitle.addEventListener('keyup', function () {
    clearTimeout(timerGameTitleSuggestions);
    timerGameTitleSuggestions = setTimeout(function () {
        getGameTitleSuggestions(gameTitle.value);
    }, 450)
});

function getGameTitleSuggestions(input) {
    $.ajax({
        url: '/api/games/allGames',
        type: 'GET',
        success: function (data) {
            gameTitleHintsResult = data;
            showGameTitleSuggestions(input);
            if (gameListSuggestions.children.length > 0 && gameTitle.value !== '') {
                gameListSuggestions.classList.add("show-notification");
                gameListSuggestions.classList.remove("hide-notification");
            } else {
                gameListSuggestions.classList.add("hide-notification");
                gameListSuggestions.classList.remove("show-notification");
            }
        },
        error: function (error) {
            console.error('Error: ', error);
        }
    });
}

function showGameTitleSuggestions(input) {
    let filteredGameTitles = gameTitleHintsResult.filter(suggestion => suggestion.toLowerCase().trim()
        .includes(input.trim().toLowerCase()))
    filteredGameTitles = filteredGameTitles.sort((a, b) => a.localeCompare(b));
    if (!input) {
        gameListSuggestions.innerHTML = '';
        return;
    }
    gameListSuggestions.innerHTML = '';
    filteredGameTitles.forEach(suggestion => {
        let listItem = document.createElement('li');
        listItem.className = 'hint-listing';
        listItem.textContent = suggestion;
        gameListSuggestions.appendChild(listItem);
    });
}

//FORM PREVENT DEFAULT BEFORE VALIDATION

// let gameForm = document.getElementById("add-content-form");
// gameForm.addEventListener('submit',function (ev){
//     ev.preventDefault();
// })

//SHOW PRODUCER SUGGESTION
let gameProducer = document.getElementById('producer-name');
let gameProducerList = document.getElementById('show-producers-suggestion-list');
let gameProducerFormValue = document.getElementById('producer-value');
let gameProducerError = document.getElementById('game-producer-error');
let gameProducerTimer;
let gameProducerHintResult = [];

gameProducer.addEventListener('keyup', function () {
    clearTimeout(gameProducerTimer);
    gameProducerTimer = setTimeout(function () {
        getProducersSuggestion(gameProducer.value);
    }, 450)
});

function getProducersSuggestion(input) {
    $.ajax({
        url: '/api/company/allProducers',
        type: 'GET',
        success: function (data) {
            gameProducerHintResult = data;
            showProducers(input);
            if (gameProducerList.children.length > 0 && gameProducer.value !== '') {
                gameProducerList.classList.add("show-notification");
                gameProducerList.classList.remove("hide-notification");
            } else {
                gameProducerList.classList.add("hide-notification");
                gameProducerList.classList.remove("show-notification");
            }
        },
        error: function (error) {
            console.error('Error: ', error);
        }
    });
}

function showProducers(input) {
    let filteredGameProducers = gameProducerHintResult.filter(suggestion => suggestion.toLowerCase().trim()
        .includes(input.trim().toLowerCase()))
    filteredGameProducers = filteredGameProducers.sort((a, b) => a.localeCompare(b)).slice(0, 8);
    if (!input.trim()) {
        gameProducerList.innerHTML = '';
        return;
    }
    gameProducerList.innerHTML = '';
    filteredGameProducers.forEach(suggestion => {
        let listItem = document.createElement('li');
        listItem.className = 'hint-listing hint-listing-producer';
        let listButton = document.createElement('button');
        listButton.className = 'hint-element';
        listButton.type = 'button';
        listButton.value = suggestion;
        listButton.textContent = suggestion;
        listItem.appendChild(listButton);
        gameProducerList.appendChild(listItem);

        listButton.addEventListener('click', () => {
            gameProducer.value = listButton.value;
            gameProducerFormValue.value = (listButton.value);
            validateGameProducer();
        })
    });
}

//SHOW PUBLISHER SUGGESTION

let gamePublisher = document.getElementById('publisher-name');
let gamePublisherList = document.getElementById('show-publishers-suggestion-list');
let gamePublisherFormValue = document.getElementById('publisher-value');
let gamePublisherError = document.getElementById('game-publisher-error');
let gamePublisherTimer;
let gamePublisherHintResult = [];

gamePublisher.addEventListener('keyup', function () {
    clearTimeout(gamePublisherTimer);
    gamePublisherTimer = setTimeout(function () {
        getPublisherSuggestion(gamePublisher.value);
    }, 450)
});

function getPublisherSuggestion(input) {
    $.ajax({
        url: '/api/company/allPublishers',
        type: 'GET',
        success: function (data) {
            gamePublisherHintResult = data;
            showPublishers(input);
            if (gamePublisherList.children.length > 0 && gamePublisher.value !== '') {
                gamePublisherList.classList.add("show-notification");
                gamePublisherList.classList.remove("hide-notification");
            } else {
                gamePublisherList.classList.add("hide-notification");
                gamePublisherList.classList.remove("show-notification");
            }
        },
        error: function (error) {
            console.error('Error: ', error);
        }
    });
}

function showPublishers(input) {
    let filteredGamePublishers = gamePublisherHintResult.filter(suggestion => suggestion.toLowerCase().trim()
        .includes(input.trim().toLowerCase()))
    filteredGamePublishers = filteredGamePublishers.sort((a, b) => a.localeCompare(b)).slice(0, 8);
    if (!input.trim()) {
        gamePublisherList.innerHTML = '';
        return;
    }
    gamePublisherList.innerHTML = '';
    filteredGamePublishers.forEach(suggestion => {
        let listItem = document.createElement('li');
        listItem.className = 'hint-listing hint-listing-publisher';
        let listButton = document.createElement('button');
        listButton.className = 'hint-element';
        listButton.type = 'button';
        listButton.value = suggestion;
        listButton.textContent = suggestion;
        listItem.appendChild(listButton);
        gamePublisherList.appendChild(listItem);

        listButton.addEventListener('click', () => {
            gamePublisher.value = listButton.value;
            gamePublisherFormValue.value = (listButton.value);
            validateGamePublisher();
        })
    });
}

// VALIDATE GAME TITLE

let gameTitleError = document.getElementById("title-error");
const validateGameTitle = async () => {
    let gameTitleValue = gameTitle.value.trim();
    if (gameTitleValue === '') {
        gameTitle.classList.add('error-input');
        gameTitleError.innerHTML = 'Tytuł nie może być pusty!';
        return false;
    }
    if (gameTitleValue.length < 2) {
        gameTitle.classList.add('error-input');
        gameTitleError.innerHTML = 'Tytuł musi mieć przynajmniej 2 znaki';
        return false;
    }
    if (gameTitleValue.length > 120) {
        gameTitle.classList.add('error-input');
        gameTitleError.innerHTML = 'Tytuł może mieć maksymalnie 120 znaków';
        return false;
    }
    gameTitle.classList.remove('error-input');
    gameTitleError.innerHTML = '';
    return true;
}
// VALIDATE GAME SHORT DESCRIPTION

let gameShortDescription = document.getElementById("game-short-description");
let gameShortDescriptionError = document.getElementById("game-short-description-error")
const validateGameShortDescription = () => {
    let gameShortDescriptionValue = gameShortDescription.value.trim();
    if (gameShortDescriptionValue === '') {
        gameShortDescription.classList.add('error-input');
        gameShortDescriptionError.innerHTML = 'Opis nie może być pusty!'
        return false;
    }
    if (gameShortDescriptionValue.length < 100) {
        gameShortDescription.classList.add('error-input');
        gameShortDescriptionError.innerHTML = 'Opis musi mieć przynajmniej 100 znaków'
        return false;
    }
    if (gameShortDescriptionValue.length > 1000) {
        gameShortDescription.classList.add('error-input');
        gameShortDescriptionError.innerHTML = 'Opis może mieć maksymalnie 1000 znaków'
        return false;
    }
    gameShortDescription.classList.remove('error-input');
    gameShortDescriptionError.innerHTML = ''
    return true;
}
// VALIDATE GAME LONG DESCRIPTION

let gameLongDescription = document.getElementById("game-long-description");
let gameLongDescriptionError = document.getElementById("game-long-description-error");
const validateGameLongDescription = () => {
    let gameLongDescriptionValue = tinymce.get("game-long-description").getContent().trim();
    if (gameLongDescriptionValue === '') {
        gameLongDescription.classList.add('error-input');
        gameLongDescriptionError.innerHTML = "Opis nie może być pusty";
        return false;
    }
    if (gameLongDescriptionValue.length < 200) {
        gameLongDescription.classList.add('error-input');
        gameLongDescriptionError.innerHTML = "Opis musi mieć przynajmniej 200 znaków";
        return false;
    }
    if (gameLongDescriptionValue.length > 105_000) {
        gameLongDescription.classList.add('error-input');
        gameLongDescriptionError.innerHTML = "Opis może mieć maksymalnie 100000 znaków";
        return false;
    }
    if (!gameLongDescriptionValue.includes('<h2>')) {
        gameLongDescription.classList.add('error-input');
        gameLongDescriptionError.innerHTML = "Opis musi zawierać przynajmniej 1 nagłówek (h2)";
        return false;
    }
    gameLongDescription.classList.remove('error-input');
    gameLongDescriptionError.innerHTML = "";
    return true;
}
let gameTrailerId = document.getElementById("game-trailer-id");
let gameTrailerError = document.getElementById("game-trailer-id-error");

const validateGameTrailerId = () => {
    let gameTrailerIdValue = gameTrailerId.value.trim();
    if (gameTrailerIdValue === '') {
        gameTrailerId.classList.add('error-input');
        gameTrailerError.innerHTML = 'Id zwiastunu nie może być puste'
        return false;
    }
    if (gameTrailerIdValue.length < 4) {
        gameTrailerId.classList.add('error-input');
        gameTrailerError.innerHTML = 'Id zwiastunu musi mieć przynajmniej 4 znaki'
        return false;
    }
    if (gameTrailerIdValue.length > 30) {
        gameTrailerId.classList.add('error-input');
        gameTrailerError.innerHTML = 'Id zwiastunu może mieć maksymalnie 30 znaków'
        return false;
    }
    gameTrailerId.classList.remove('error-input');
    gameTrailerError.innerHTML = '';
    return true;
}
//VALIDATE GAME PRODUCER
const validateGameProducer = () => {
    let gameProducerValue = gameProducerFormValue.value.trim();
    if (gameProducerValue === '') {
        gameProducerFormValue.classList.add('error-input');
        gameProducerError.innerHTML = 'Producent nie może być pusty';
        return false;
    }
    if (gameProducerValue.length < 3) {
        gameProducerFormValue.classList.add('error-input');
        gameProducerError.innerHTML = 'Producent musi mieć przynajmniej 3 znaki';
        return false;
    }
    if (gameProducerValue.length > 100) {
        gameProducerFormValue.classList.add('error-input');
        gameProducerError.innerHTML = 'Producent może mieć maksymalnie 3 znaki';
        return false;
    }
    if (gameProducerValue !== gameProducer.value) {
        gameProducerFormValue.classList.add('error-input');
        gameProducerError.innerHTML = 'Wartośći w obu polach muszą być takie same';
        return false;
    }
    gameProducerFormValue.classList.remove('error-input');
    gameProducerError.innerHTML = '';
    return true
}
const validateGamePublisher = () => {
    let gamePublisherValue = gamePublisherFormValue.value.trim();
    if (gamePublisherValue === '') {
        gamePublisherFormValue.classList.add('error-input');
        gamePublisherError.innerHTML = 'Wydawca nie może być pusty';
        return false;
    }
    if (gamePublisherValue.length < 3) {
        gamePublisherFormValue.classList.add('error-input');
        gamePublisherError.innerHTML = 'Wydawca musi mieć przynajmniej 3 znaki';
        return false;
    }
    if (gamePublisherValue.length > 100) {
        gamePublisherFormValue.classList.add('error-input');
        gamePublisherError.innerHTML = 'Wydawca może mieć maksymalnie 3 znaki';
        return false;
    }
    if (gamePublisherValue !== gamePublisher.value) {
        gamePublisherFormValue.classList.add('error-input');
        gamePublisherError.innerHTML = 'Wartośći w obu polach muszą być takie same';
        return false;
    }
    gamePublisherFormValue.classList.remove('error-input');
    gamePublisherError.innerHTML = '';
    return true
}
let releaseDate = document.getElementById('release-date');
let releaseDateError = document.getElementById('release-date-error');
const validateGameReleaseDate = () => {
    let releaseDateValue = releaseDate.value;
    let formDate = new Date(releaseDateValue);

    if(releaseDateValue === ''){
        releaseDate.classList.add('error-input');
        releaseDateError.innerHTML='Data nie może być pusta';
        return false;
    }

    if(formDate.getFullYear() < 1980){
        releaseDate.classList.add('error-input');
        releaseDateError.innerHTML='Rok nie może być wcześniejszy niż 1980';
        return false;
    }
    if(formDate.getFullYear() > 2099){
        releaseDate.classList.add('error-input');
        releaseDateError.innerHTML='Rok nie może być poźniejszy niż 2099';
        return false;
    }

    releaseDate.classList.remove('error-input');
    releaseDateError.innerHTML='';
    return true;
}