//DISABLE FORM BEFORE VALIDATION
const gameFormButton = document.getElementById("game-add-form-button");
const gameForm = document.getElementById("add-content-form")
gameForm.addEventListener("submit", function (ev) {
    ev.preventDefault();
})

// SHOW TITLE SUGGESTION
const gameTitle = document.getElementById("game-title");
const gameListSuggestions = document.getElementById("show-title-suggestion-list");
let timerGameTitleSuggestions;
let gameTitleHintsResult = [];

gameTitle.addEventListener('keyup', function () {
    clearTimeout(timerGameTitleSuggestions);
    timerGameTitleSuggestions = setTimeout(function () {
        getGameTitleSuggestions(gameTitle.value);
    }, 450)
});

function getGameTitleSuggestions(input) {
    if (gameTitleHintsResult.length > 1) {
        showGameTitleSuggestions(input);
    } else {

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

function checkGameTitleAvailability(title) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: '/api/games/availability',
            type: 'GET',
            data: {title: title},
            success: function (data) {
                if (!data) {
                    resolve(false);
                } else {
                    resolve(true);
                }
            },
            error: function (error) {
                console.error('Error: ', error);
                reject(error);
            }
        })
    })
}


//SHOW PRODUCER SUGGESTION
const gameProducer = document.getElementById('producer-name');
const gameProducerList = document.getElementById('show-producers-suggestion-list');
const gameProducerFormValue = document.getElementById('producer-value');
const gameProducerError = document.getElementById('game-producer-error');
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

const gamePublisher = document.getElementById('publisher-name');
const gamePublisherList = document.getElementById('show-publishers-suggestion-list');
const gamePublisherFormValue = document.getElementById('publisher-value');
const gamePublisherError = document.getElementById('game-publisher-error');
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

const gameTitleError = document.getElementById("title-error");
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

    let isTitleAvailable = await checkGameTitleAvailability(gameTitleValue);

    if (!isTitleAvailable) {
        gameTitle.classList.add('error-input');
        gameTitleError.innerHTML = 'Gra już istnieje';
        return false;
    }
    if (gameTitleValue.includes("<script>")) {
        gameTitle.classList.add('error-input');
        gameTitleError.innerHTML = 'Tytuł zawiera niedozwolone frazy';
        return false;
    }

    if (gameTitleValue.length > 99) {
        gameTitle.classList.add('error-input');
        gameTitleError.innerHTML = 'Tytuł może mieć maksymalnie 99 znaków';
        return false;
    }
    gameTitle.classList.remove('error-input');
    gameTitleError.innerHTML = '';
    return true;
}
// VALIDATE GAME SHORT DESCRIPTION

const gameShortDescription = document.getElementById("game-short-description");
const gameShortDescriptionError = document.getElementById("game-short-description-error")
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
    let reg = "<\\w+\\s*[^>]*>";
    if (gameShortDescriptionValue.match(reg)) {
        gameShortDescription.classList.add('error-input');
        gameShortDescriptionError.innerHTML = "Opis nie może zawierac tagów html!";
        return false;
    }
    if (gameShortDescriptionValue.length > 320) {
        gameShortDescription.classList.add('error-input');
        gameShortDescriptionError.innerHTML = 'Opis może mieć maksymalnie 320 znaków'
        return false;
    }
    gameShortDescription.classList.remove('error-input');
    gameShortDescriptionError.innerHTML = ''
    return true;
}
// VALIDATE GAME LONG DESCRIPTION

const gameLongDescription = document.getElementById("game-long-description");
const gameLongDescriptionError = document.getElementById("game-long-description-error");
let gameLongDescriptionValue;
const validateGameLongDescription = () => {
    try {
        gameLongDescriptionValue = tinymce.get("game-long-description").getContent().trim();
    } catch (e) {
        if (e instanceof ReferenceError)
            gameLongDescriptionValue = gameLongDescription.value.trim();
    }

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
        gameLongDescriptionError.innerHTML = "Opis może mieć maksymalnie 105000 znaków";
        return false;
    }
    if (!(gameLongDescriptionValue.includes('<h2>') && gameLongDescriptionValue.includes('</h2>'))) {
        gameLongDescription.classList.add('error-input');
        gameLongDescriptionError.innerHTML = "Opis musi zawierać przynajmniej 1 nagłówek &lt;h2&gt; &lt;/h2&gt; ";
        return false;
    }
    if (gameLongDescriptionValue.includes('<h1>') || gameLongDescriptionValue.includes('<h3>') ||
        gameLongDescriptionValue.includes('<h4>') || gameLongDescriptionValue.includes('<h5>') ||
        gameLongDescriptionValue.includes('<h6>')) {
        gameLongDescription.classList.add('error-input');
        gameLongDescriptionError.innerHTML = "Opis może zawierac tylko nagłówki &lt;h2&gt; ";
        return false;
    }
    if (gameLongDescriptionValue.includes("<script>")) {
        gameLongDescription.classList.add('error-input');
        gameLongDescriptionError.innerHTML = 'Opis zawiera niedozwolone frazy';
        return false;
    }
    gameLongDescription.classList.remove('error-input');
    gameLongDescriptionError.innerHTML = "";
    return true;
}
//VALIDATE GAME TRAILER ID
const gameTrailerId = document.getElementById("game-trailer-id");
const gameTrailerError = document.getElementById("game-trailer-id-error");

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
//VALIDATE GAME PUBLISHER
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

//VALIDATE RELEASE DATE
const releaseDateContainer = document.getElementById('game-date-input-container');
let releaseDate;
let releaseDateError;
let releaseDateValue = [];
let todayDate = new Date();
todayDate.setHours(23);
todayDate.setMinutes(59);
todayDate.setSeconds(59);
todayDate.setMilliseconds(0);
let isPremiereInFuture;

const validateGameReleaseDate = () => {
    releaseDate = document.querySelectorAll('.date-platform-value');
    releaseDateError = document.getElementById('release-date-error');
    for (let i = 0; i < releaseDate.length; i++) {
        releaseDateValue[i] = new Date(releaseDate[i].value);
    }
    if(releaseDateValue.length===0){
        releaseDateContainer.classList.add('error-input');
        releaseDateError.innerHTML = 'Dodaj przynajmniej 1 datę';
        return false;
    }
    for (let date of releaseDateValue) {
        if (isNaN(date.getDate())) {
            releaseDateContainer.classList.add('error-input');
            releaseDateError.innerHTML = 'Podaj date wydania';
            return false;
        }
        if (date.getFullYear() < 1980) {
            releaseDateContainer.classList.add('error-input');
            releaseDateError.innerHTML = 'Rok nie może być wcześniejszy niż 1980';
            return false;
        }
        if ((date.getFullYear() !== 8888 && date.getFullYear() !== 9999) && date.getFullYear() > 2099) {
            releaseDateContainer.classList.add('error-input');
            releaseDateError.innerHTML = 'Rok nie może być poźniejszy niż 2099';
            return false;
        }
    }
    for (let date of releaseDateValue) {
        isPremiereInFuture = false;
        if (date.getFullYear() <= 2099 && date > todayDate) {
            bigSuggestionPosterDisplayContainer.classList.remove("hide-big-suggestion-poster");
            bigSuggestionPosterDisplayContainer.classList.add("show-big-suggestion-poster");
            isPremiereInFuture = true;
            break;
        }
    }
    if (!isPremiereInFuture) {
        bigSuggestionPosterDisplayContainer.classList.add("hide-big-suggestion-poster");
        bigSuggestionPosterDisplayContainer.classList.remove("show-big-suggestion-poster");
        isPremiereInFuture = false;
        removeBigSuggestionPoster();
    }
    releaseDateContainer.classList.remove('error-input');
    releaseDateError.innerHTML = '';
    return true;
}

//VALIDATE GAME MODE
const singlePlayerCheckBox = document.getElementById("gameModes1");
const multiplayerCheckBox = document.getElementById("gameModes2");
const showPlayersRange = document.getElementById("show-players-range");
const minPlayers = document.getElementById("min-players");
const maxPlayers = document.getElementById("max-players");
const gameModesError = document.getElementById("game-modes-error");
const playerRangeError = document.getElementById("player-range-error");
const gameModesContainer = document.getElementById("game-modes-input-container");

const validateGameMode = () => {
    if (multiplayerCheckBox.checked) {
        (showPlayersRange.classList.contains("hide-players-range"))
        showPlayersRange.classList.remove("hide-players-range");
        showPlayersRange.classList.add("show-players-range");
    } else {
        showPlayersRange.classList.add("hide-players-range");
        showPlayersRange.classList.remove("show-players-range");
        minPlayers.value = 1;
        maxPlayers.value = 1;
        validatePlayersRange();
    }
    if (!singlePlayerCheckBox.checked && !multiplayerCheckBox.checked) {
        gameModesContainer.classList.add("error-input");
        gameModesError.innerHTML = "Wybierz przynajmniej 1 opcje"
        return false;
    }
    gameModesContainer.classList.remove("error-input");
    gameModesError.innerHTML = "";
    return true;
}
//VALIDATE PLAYERS RANGE
const validatePlayersRange = () => {
    let minPlayersValue;
    let maxPlayerValue;
    if (!Number.isNaN(minPlayers) && !Number.isNaN(maxPlayers)) {
        minPlayersValue = parseInt(minPlayers.value);
        maxPlayerValue = parseInt(maxPlayers.value);
    }

    if (minPlayersValue > maxPlayerValue) {
        minPlayers.classList.add("error-input");
        playerRangeError.innerHTML = "Minimalna wartość jest większa niż maksymalna";
        return false;
    }
    if (minPlayersValue < 1) {
        minPlayers.classList.add("error-input");
        playerRangeError.innerHTML = "Minimalna wartość to 1";
        return false;
    }
    if (minPlayersValue > 1000) {
        minPlayers.classList.add("error-input");
        playerRangeError.innerHTML = "Maksymalna wartość dla min to 1000";
        return false;
    }
    if (maxPlayerValue < 1) {
        maxPlayers.classList.add("error-input");
        playerRangeError.innerHTML = "Minimalna wartość to 1";
        return false;
    }
    if (maxPlayerValue > 2000) {
        maxPlayers.classList.add("error-input");
        playerRangeError.innerHTML = "Maksymalna wartość dla max to 2000";
        return false;
    }

    minPlayers.classList.remove("error-input");
    maxPlayers.classList.remove("error-input");
    playerRangeError.innerHTML = "";
    return true;
}
//VALIDATE GAME PLATFORMS

const platform = document.getElementById("platforms");
const platformsError = document.getElementById("game-platforms-error");
const validateGamePlatforms = () => {
    const itemPlatforms = document.querySelectorAll(".item-platforms");

    if (itemPlatforms.length < 1) {
        platform.classList.add("error-input");
        platformsError.innerHTML = "Dodaj przynajmniej 1 platformę";
        return false;
    }
    if (itemPlatforms.length > 12) {
        platform.classList.add("error-input");
        platformsError.innerHTML = "Maksymalna ilość platform to 12";
        return false;
    }
    platform.classList.remove("error-input");
    platformsError.innerHTML = "";
    return true;
}
//VALIDATE CATEGORIES

const categories = document.getElementById("categories");
const categoriesError = document.getElementById("game-categories-error");

function validateCategories() {
    const itemCategories = document.querySelectorAll(".item-categories");

    if (itemCategories.length < 4) {
        categories.classList.add("error-input");
        categoriesError.innerHTML = "Dodaj przynajmniej 4 kategorie";
        return false;
    }
    if (itemCategories.length > 14) {
        categories.classList.add("error-input");
        categoriesError.innerHTML = "Maksymalna ilość kategorii to 14";
        return false;
    }
    if (!document.querySelector(".chosen")) {
        categories.classList.add("error-input");
        categoriesError.innerHTML = "Wybierz główną kategorię";
        return false;
    }
    categories.classList.remove("error-input");
    categoriesError.innerHTML = "";
    return true;
}

//VALIDATE POSTER
const gamePoster = document.getElementById("game-poster");
const posterError = document.getElementById("poster-error");
const gamePosterHeightWidth = document.getElementById("game-poster-proportion-height");
const gamePosterWidthHeight = document.getElementById("game-poster-proportion-width");

const validateGamePoster = async () => {
    let size;
    let extension;
    let width;
    let height;
    let mimeType;
    let proportionHeightWidth;
    let proportionWidthHeight
    gamePosterHeightWidth.innerHTML = '';
    gamePosterWidthHeight.innerHTML = '';


    if (gamePoster.value !== "") {
        size = ((gamePoster.files[0].size / 1024) / 1024).toString().slice(0, 4);
        extension = gamePoster.files[0].type;

        const img = new Image();
        img.src = window.URL.createObjectURL(gamePoster.files[0]);
        await new Promise((resolve) => {
            img.onload = async () => {
                width = img.width;
                height = img.height;
                try {
                    mimeType = await checkMimeType(gamePoster.files[0]);
                } catch (error) {
                    console.error(error);
                }
                resolve();
            };
        });
    }
    proportionHeightWidth = height / width;
    let slicedProportionHeightWidth = proportionHeightWidth.toString().slice(0, 4);

    proportionWidthHeight = width / height;
    let slicedProportionWidthHeight = proportionWidthHeight.toString().slice(0, 4);


    if (gamePoster.value === "") {
        gamePoster.classList.add("error-input");
        posterError.innerHTML = "Dodaj obraz";
        removeGamePoster();
        return false;
    }
    if (gamePoster.value !== "" && size > 1) {
        gamePoster.classList.add("error-input");
        posterError.innerHTML = "Obraz jest za duży " + size + "Mb";
        return false;
    }
    if (mimeType !== extension) {
        gamePoster.classList.add("error-input");
        posterError.innerHTML = "Obraz jest uszkodzony i ma niepoprawne rozszerzenie wzgledem MIME(" + mimeType + ")";
        return false;
    }
    if (gamePoster.value !== "" && height > 1200) {
        gamePoster.classList.add("error-input");
        posterError.innerHTML = "Wysokość obrazu jest za duża " + height + "px" + " (max 1200px)";
        return false;
    }
    if (gamePoster.value !== "" && width > 800) {
        gamePoster.classList.add("error-input");
        posterError.innerHTML = "Szerokość obrazu jest za duża " + width + "px" + " (max 800px)";
        return false;
    }
    if (gamePoster.value !== "" && height < 600) {
        gamePoster.classList.add("error-input");
        posterError.innerHTML = "Wysokość obrazu jest za mała " + height + "px" + " (min 600px)";
        return false;
    }
    if (gamePoster.value !== "" && width < 400) {
        gamePoster.classList.add("error-input");
        posterError.innerHTML = "Szerokość obrazu jest za mała " + width + "px" + " (min 400px)";
        return false;
    }

    if (proportionHeightWidth < 1.3 || proportionHeightWidth > 1.5) {
        gamePoster.classList.add("error-input");
        posterError.innerHTML = "Proporcje wysokośc/szerokość powinna być 1.3-1.5 jest " + slicedProportionHeightWidth;
        return false;
    }
    if (proportionWidthHeight < 0.6 || proportionWidthHeight > 0.8) {
        gamePoster.classList.add("error-input");
        posterError.innerHTML = "Proporcje szerokośc/wysokość powinna być 0.6-0.8 jest " + slicedProportionWidthHeight;
        return false;
    }
    if (extension !== "image/jpeg" && extension !== "image/png" && extension !== "image/jpg" && gamePoster.value !== "") {
        gamePoster.classList.add("error-input");
        posterError.innerHTML = "Nieobsługiwany format pliku!";
        removeGamePoster();
        return false;
    }
    gamePosterHeightWidth.innerHTML = "wysokość/szerokość = " + slicedProportionHeightWidth;
    gamePosterWidthHeight.innerHTML = "szerokość/wysokość = " + slicedProportionWidthHeight;

    gamePoster.classList.remove("error-input");
    posterError.innerHTML = "";
    return true;
}
//VALIDATE GAME SUGGESTION POSTER BIG
const bigSuggestionPoster = document.getElementById("big-suggestion-poster");
const bigSuggestionPosterError = document.getElementById("big-suggestion-poster-error");
const bigSuggestionPosterDisplayContainer = document.getElementById("big-suggestion-poster-display");
const bigSuggestionWidthHeight = document.getElementById("big-suggestion-proportion-height");
const bigSuggestionHeightWidth = document.getElementById("big-suggestion-proportion-width");

const validateBigSuggestionPoster = async () => {
    let size;
    let extension;
    let bigPosterWidth;
    let bigPosterHeight;
    let bigPosterProportionHeightWidth;
    let bigPosterSlicedProportionHeightWidth;
    let bigPosterProportionWidthHeight;
    let bigPosterSlicedProportionWidthHeight;
    let mimeType = '';
    bigSuggestionWidthHeight.innerHTML = '';
    bigSuggestionHeightWidth.innerHTML = '';

    if (bigSuggestionPoster.value !== "") {
        size = ((bigSuggestionPoster.files[0].size / 1024) / 1024).toString().slice(0, 4);
        extension = bigSuggestionPoster.files[0].type;

        const img = new Image();
        img.src = window.URL.createObjectURL(bigSuggestionPoster.files[0]);
        await new Promise((resolve) => {
            img.onload = async () => {
                bigPosterWidth = img.width;
                bigPosterHeight = img.height;
                bigPosterProportionHeightWidth = bigPosterHeight / bigPosterWidth;
                bigPosterSlicedProportionHeightWidth = bigPosterProportionHeightWidth.toString().slice(0, 4);

                bigPosterProportionWidthHeight = bigPosterWidth / bigPosterHeight;
                bigPosterSlicedProportionWidthHeight = bigPosterProportionWidthHeight.toString().slice(0, 4);

                try {
                    mimeType = await checkMimeType(bigSuggestionPoster.files[0]);
                } catch (error) {
                    console.error(error);
                }
                resolve();
            };
        });

    }


    if (bigSuggestionPoster.value === "") {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Dodaj obraz";
        removeBigSuggestionPoster();
        return false;
    }
    if (mimeType !== extension) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Obraz jest uszkodzony i ma niepoprawne rozszerzenie wzgledem MIME(" + mimeType + ")";
        return false;
    }
    if ((bigSuggestionPoster.value !== "" && size > 1)) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Obraz jest za duży " + size + "Mb";
        return false;
    }
    if ((bigSuggestionPoster.value !== "" && bigPosterHeight > 1200)) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Wysokość obrazu jest za duża " + bigPosterHeight + "px" + " (max 1200px)";
        return false;
    }
    if ((bigSuggestionPoster.value !== "" && bigPosterWidth > 2400)) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Szerokość obrazu jest za duża " + bigPosterWidth + "px" + " (max 2400px)";
        return false;
    }
    if ((bigSuggestionPoster.value !== "" && bigPosterHeight < 350)) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Wysokość obrazu jest za mała " + bigPosterHeight + "px" + " (min 350px)";
        return false;
    }
    if ((bigSuggestionPoster.value !== "" && bigPosterWidth < 1100)) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Szerokość obrazu jest za mała " + bigPosterWidth + "px" + " (min 1100px)";
        return false;
    }
    if (bigPosterProportionHeightWidth < 0.30 || bigPosterProportionHeightWidth > 0.60) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Proporcje wysokośc/szerokość powinna być 0.3-0.6 jest " + bigPosterSlicedProportionHeightWidth;
        return false;
    }
    if (bigPosterProportionWidthHeight < 2 || bigPosterProportionWidthHeight > 3.3) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Proporcje szerokośc/wysokość powinna być 2.0-3.3 jest " + bigPosterSlicedProportionWidthHeight;
        return false;
    }
    if (bigSuggestionPoster.value !== "" && extension !== "image/jpeg" && extension !== "image/png" && extension !== "image/jpg") {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Nieobsługiwany format pliku!";
        removeBigSuggestionPoster();
        return false;
    }
    bigSuggestionHeightWidth.innerHTML = "wysokość/szerokość = " + bigPosterSlicedProportionHeightWidth;
    bigSuggestionWidthHeight.innerHTML = "szerokość/wysokość = " + bigPosterSlicedProportionWidthHeight;

    bigSuggestionPoster.classList.remove("error-input");
    bigSuggestionPosterError.innerHTML = "";
    return true;
}

//CHECK MIME TYPE EXTENSION FROM FILE
function checkMimeType(file) {
    return new Promise((resolve, reject) => {
        if (file != null) {
            const reader = new FileReader();

            reader.onloadend = function () {
                const arr = (new Uint8Array(reader.result)).subarray(0, 4);
                let header = "";
                for (let i = 0; i < arr.length; i++) {
                    header += arr[i].toString(16);
                }
                let mime = "";
                switch (header) {
                    case "89504e47":
                        mime = "image/png";
                        break;
                    case "ffd8ffe0":
                    case "ffd8ffe1":
                    case "ffd8ffe2":
                        mime = "image/jpeg";
                        break;
                    default:
                        mime = "unknown";
                        break;
                }
                resolve(mime);
            };
            reader.onerror = reject;
            reader.readAsArrayBuffer(file);
        } else {
            reject('No file provided');
        }
    });
}

//VALIDATE SMALL SUGGESTION POSTER
const smallSuggestionPoster = document.getElementById("small-suggestion-poster");
const smallSuggestionPosterError = document.getElementById("small-suggestion-poster-error");
const smallSuggestionWidthHeight = document.getElementById("small-suggestion-proportion-height");
const smallSuggestionHeightWidth = document.getElementById("small-suggestion-proportion-width");
const validateSmallSuggestionPoster = async () => {
    let size;
    let extension;
    let width;
    let height;
    let mimeType;
    let proportionHeightWidth;
    let slicedProportionHeightWidth;
    let proportionWidthHeight;
    let slicedProportionWidthHeight;

    smallSuggestionWidthHeight.innerHTML = '';
    smallSuggestionHeightWidth.innerHTML = '';

    if (smallSuggestionPoster.value !== "") {
        size = ((smallSuggestionPoster.files[0].size / 1024) / 1024).toString().slice(0, 4);
        extension = smallSuggestionPoster.files[0].type;

        const img = new Image();
        img.src = window.URL.createObjectURL(smallSuggestionPoster.files[0]);
        await new Promise((resolve) => {
            img.onload = async () => {
                width = img.width;
                height = img.height;
                proportionHeightWidth = height / width;
                slicedProportionHeightWidth = proportionHeightWidth.toString().slice(0, 4);

                proportionWidthHeight = width / height;
                slicedProportionWidthHeight = proportionWidthHeight.toString().slice(0, 4);
                try {
                    mimeType = await checkMimeType(smallSuggestionPoster.files[0]);
                } catch (error) {
                    console.error(error);
                }
                resolve();
            };
        });

    }
    if (smallSuggestionPoster.value === "") {
        smallSuggestionPoster.classList.add("error-input");
        smallSuggestionPosterError.innerHTML = "Dodaj obraz";
        removeSmallSuggestionPoster();
        return false;
    }
    if (mimeType !== extension) {
        smallSuggestionPoster.classList.add("error-input");
        smallSuggestionPosterError.innerHTML = "Obraz jest uszkodzony i ma niepoprawne rozszerzenie wzgledem MIME(" + mimeType + ")";
        return false;
    }
    if ((smallSuggestionPoster.value !== "" && size > 1)) {
        smallSuggestionPoster.classList.add("error-input");
        smallSuggestionPosterError.innerHTML = "Obraz jest za duży " + size + "Mb";
        return false;
    }
    if ((smallSuggestionPoster.value !== "" && height < 200)) {
        smallSuggestionPoster.classList.add("error-input");
        smallSuggestionPosterError.innerHTML = "Wysokość obrazu jest za mała " + height + "px" + " (min 200px)";
        return false;
    }
    if ((smallSuggestionPoster.value !== "" && height > 600)) {
        smallSuggestionPoster.classList.add("error-input");
        smallSuggestionPosterError.innerHTML = "Wysokość obrazu jest za duża " + height + "px" + " (max 600px)";
        return false;
    }
    if ((smallSuggestionPoster.value !== "" && width > 1800)) {
        smallSuggestionPoster.classList.add("error-input");
        smallSuggestionPosterError.innerHTML = "Szerokość obrazu jest za duża " + width + "px" + " (max 1800px)";
        return false;
    }
    if ((smallSuggestionPoster.value !== "" && width < 200)) {
        smallSuggestionPoster.classList.add("error-input");
        smallSuggestionPosterError.innerHTML = "Szerokość obrazu jest za mała " + width + "px" + " (min 600px)";
        return false;
    }
    if (proportionHeightWidth < 0.3 || proportionHeightWidth > 0.60) {
        smallSuggestionPoster.classList.add("error-input");
        smallSuggestionPosterError.innerHTML = "Proporcje wysokośc/szerokość powinna być 0.3-0.6 jest " + slicedProportionHeightWidth;
        return false;
    }
    if (proportionWidthHeight < 2.6 || proportionWidthHeight > 3.4) {
        smallSuggestionPoster.classList.add("error-input");
        smallSuggestionPosterError.innerHTML = "Proporcje szerokośc/wysokość powinna być 2.6-3.4 jest " + slicedProportionWidthHeight;
        return false;
    }
    if (smallSuggestionPoster.value !== "" && extension !== "image/jpeg" && extension !== "image/png" && extension !== "image/jpg") {
        smallSuggestionPoster.classList.add("error-input");
        smallSuggestionPosterError.innerHTML = "Nieobsługiwany format pliku!";
        removeSmallSuggestionPoster();
        return false;
    }

    smallSuggestionHeightWidth.innerHTML = "wysokość/szerokość = " + slicedProportionHeightWidth;
    smallSuggestionWidthHeight.innerHTML = "szerokość/wysokość = " + slicedProportionWidthHeight;

    smallSuggestionPoster.classList.remove("error-input");
    smallSuggestionPosterError.innerHTML = "";
    return true;
}

//VALIDATE IF FORM CONTAINS ERROR
async function validateGameForm() {
    let gameTitle = await validateGameTitle();
    let gameShortDescription = validateGameShortDescription();
    let gameLongDescription = validateGameLongDescription();
    let trailerId = validateGameTrailerId();
    let producer = validateGameProducer();
    let publisher = validateGamePublisher();
    let date = validateGameReleaseDate();
    let categories = validateCategories();
    let gameModes = validateGameMode();
    let playerRange = validatePlayersRange();
    let platforms = validateGamePlatforms();
    let poster = await validateGamePoster();
    let smallPoster = await validateSmallSuggestionPoster();
    let bigPoster;
    if (isPremiereInFuture) {
        bigPoster = await validateBigSuggestionPoster();
    } else {
        bigPoster = true;
    }
    return gameTitle && gameShortDescription && gameLongDescription && trailerId && producer && publisher &&
        date && gameModes && playerRange && platforms && poster && bigPoster && categories && smallPoster;
}

function sendForm() {
    validateGameForm().then(result => {
        if (result) {
            createMultiSelectDates();
            gameForm.submit();
        } else {
            gameTitleError.classList.add("button-error");
            gameShortDescriptionError.classList.add("button-error");
            gameLongDescriptionError.classList.add("button-error");
            gameTrailerError.classList.add("button-error");
            gameProducerError.classList.add("button-error");
            gamePublisherError.classList.add("button-error");
            releaseDateError.classList.add("button-error");
            categoriesError.classList.add("button-error");
            gameModesError.classList.add("button-error");
            playerRangeError.classList.add("button-error");
            platformsError.classList.add("button-error");
            posterError.classList.add("button-error");
            bigSuggestionPosterError.classList.add("button-error");
            gameFormButton.classList.add("button-error");
            smallSuggestionPosterError.classList.add("button-error");

        }
        setTimeout(function () {
            gameTitleError.classList.remove("button-error");
            gameShortDescriptionError.classList.remove("button-error");
            gameLongDescriptionError.classList.remove("button-error");
            gameTrailerError.classList.remove("button-error");
            gameProducerError.classList.remove("button-error");
            gamePublisherError.classList.remove("button-error");
            releaseDateError.classList.remove("button-error");
            categoriesError.classList.remove("button-error");
            gameModesError.classList.remove("button-error");
            playerRangeError.classList.remove("button-error");
            platformsError.classList.remove("button-error");
            posterError.classList.remove("button-error");
            bigSuggestionPosterError.classList.remove("button-error");
            gameFormButton.classList.remove("button-error");
            smallSuggestionPosterError.classList.remove("button-error");
        }, 500)
    }).catch(error => {
        console.error('Wystąpił błąd podczas walidacji', error)
    });
}

//IMAGE PREVIEW
function previewBeforeUpload(id) {
    document.querySelector("#" + id).addEventListener("change", function (e) {
        let file = e.target.files[0];
        if (file != null) {
            let url = URL.createObjectURL(file);
            if (file.type === "image/jpeg" || file.type === "image/png" || file.type === "image/jpg") {
                document.querySelector("#" + id + "-preview div").innerText = file.name;
                document.querySelector("#" + id + "-preview img").src = url;
            }
        }
    });

}

//REMOVE GAME POSTER
function removeGamePoster(id = "game-poster") {
    document.querySelector("#" + id + "-preview div").innerHTML = "<span>+</span>";
    document.querySelector("#" + id + "-preview img").src = '/img/notfound.jpg';
}

gamePoster.addEventListener("input", function () {
    previewBeforeUpload("game-poster");
});

//REMOVE BIG SUGGESTION POSTER
function removeBigSuggestionPoster(id = "big-suggestion-poster") {
    document.querySelector("#" + id + "-preview div").innerHTML = "<span>+</span>";
    document.querySelector("#" + id + "-preview img").src = '/img/notfoundhorizontal.jpg';
    bigSuggestionPoster.value = '';
}

bigSuggestionPoster.addEventListener("input", function () {
    previewBeforeUpload("big-suggestion-poster");
});

//REMOVE SMALL SUGGESTION POSTER
function removeSmallSuggestionPoster(id = "small-suggestion-poster") {
    document.querySelector("#" + id + "-preview div").innerHTML = "<span>+</span>";
    document.querySelector("#" + id + "-preview img").src = '/img/notfoundhorizontal.jpg';
}

smallSuggestionPoster.addEventListener("input", function () {
    previewBeforeUpload("small-suggestion-poster");
});


//RESET FORM BUTTON

function clearGameForm() {
    gameForm.reset();

    gameTitle.classList.remove("error-input");
    gameTitleError.innerHTML = '';
    gameListSuggestions.innerHTML = '';

    gameShortDescription.classList.remove("error-input");
    gameShortDescriptionError.innerHTML = '';

    gameLongDescription.classList.remove("error-input");
    gameLongDescriptionError.innerHTML = '';

    gameTrailerId.classList.remove("error-input");
    gameTrailerError.innerHTML = '';

    gameProducerFormValue.classList.remove("error-input");
    gameProducerError.innerHTML = '';
    gameProducerList.innerHTML = '';

    gamePublisherFormValue.classList.remove("error-input");
    gamePublisherError.innerHTML = '';
    gamePublisherList.innerHTML = '';

    releaseDateContainer.classList.remove('error-input');
    releaseDateError.innerHTML = '';

    categories.classList.remove("error-input");
    categoriesError.innerHTML = '';

    showPlayersRange.classList.add("hide-players-range");
    showPlayersRange.classList.remove("show-players-range");

    gameModesContainer.classList.remove("error-input");
    gameModesError.innerHTML = "";

    platform.classList.remove("error-input");
    platformsError.innerHTML = '';

    gamePoster.classList.remove("error-input");
    posterError.innerHTML = '';
    bigSuggestionPosterError.innerHTML = '';
    bigSuggestionPosterDisplayContainer.classList.add("hide-big-suggestion-poster");
    bigSuggestionPosterDisplayContainer.classList.remove("show-big-suggestion-poster");

    bigSuggestionWidthHeight.innerHTML = '';
    bigSuggestionHeightWidth.innerHTML = '';
    gamePosterHeightWidth.innerHTML = '';
    gamePosterWidthHeight.innerHTML = '';

    smallSuggestionPosterError.innerHTML = '';
    isPremiereInFuture = false;
    removeGamePoster();
    removeBigSuggestionPoster();
    removeSmallSuggestionPoster();
    removeAllDateTag();
}

//RESET ERRORS MESSAGE WHEN PAGE LOAD
function resetErrorMessages() {
    categoriesError.innerHTML = '';
}

//ADD RELEASE DATE
const addDateContainer = (platformName) => {
    const platformBox = document.createElement('div');
    platformBox.classList.add('platform-release-date-box');
    platformBox.classList.add(platformName + "-box");

    // Create the container for checkboxes
    const releaseDateContainer = document.createElement('div');
    releaseDateContainer.classList.add('release-date-container');

    // Create the 'Nieznana' checkbox
    const unknownCheckboxDiv = document.createElement('div');
    unknownCheckboxDiv.classList.add('release-date-check-box');
    const unknownLabel = document.createElement('label');
    unknownLabel.classList.add('checkbox-container');
    unknownLabel.classList.add('date-unknown');
    unknownLabel.innerHTML = 'Nieznana';
    const unknownCheckbox = document.createElement('input');
    unknownCheckbox.type = 'checkbox';
    const unknownCheckmark = document.createElement('span');
    unknownCheckmark.classList.add('checkmark');
    unknownLabel.appendChild(unknownCheckbox);
    unknownLabel.appendChild(unknownCheckmark);
    unknownCheckboxDiv.appendChild(unknownLabel);

    // Create the 'Anulowana' checkbox
    const cancelledCheckboxDiv = document.createElement('div');
    cancelledCheckboxDiv.classList.add('release-date-check-box');
    const cancelledLabel = document.createElement('label');
    cancelledLabel.classList.add('checkbox-container');
    cancelledLabel.classList.add('date-canceled');
    cancelledLabel.innerHTML = 'Anulowana';
    const cancelledCheckbox = document.createElement('input');
    cancelledCheckbox.type = 'checkbox';
    const cancelledCheckmark = document.createElement('span');
    cancelledCheckmark.classList.add('checkmark');
    cancelledLabel.appendChild(cancelledCheckbox);
    cancelledLabel.appendChild(cancelledCheckmark);
    cancelledCheckboxDiv.appendChild(cancelledLabel);

    // Append checkboxes to the container
    releaseDateContainer.appendChild(unknownCheckboxDiv);
    releaseDateContainer.appendChild(cancelledCheckboxDiv);

    // Create the container for inputs
    const releaseDateInputs = document.createElement('div');
    releaseDateInputs.classList.add('release-date-inputs');

    // Create the platform name element
    const platformNameElement = document.createElement('p');
    platformNameElement.classList.add("date-platform-name")
    platformNameElement.textContent = platformName;

    // Create the date input element
    const dateInput = document.createElement('input');
    dateInput.classList.add("date-platform-value")
    dateInput.type = 'date';
    dateInput.min = '1980-01-01';
    dateInput.max = '2099-01-01';
    dateInput.setAttribute('oninput', 'validateGameReleaseDate()');

    // Append platform name and date input to the inputs container
    releaseDateInputs.appendChild(platformNameElement);
    releaseDateInputs.appendChild(dateInput);

    // Append all parts to the main container
    platformBox.appendChild(releaseDateContainer);
    platformBox.appendChild(releaseDateInputs);

    // Append the main container to the release-date box
    document.getElementById('release-date').appendChild(platformBox);

    // Pass the new field to the dateUnknown function
    dateUnknown(platformBox);
}
const removeDateTag = (platformName) => {
    document.querySelector("." + platformName + "-box").remove();
}
const removeAllDateTag = () => {
    const dateBox = document.getElementById("release-date");
    dateBox.textContent = '';
}
const dateUnknown = (newField) => {
    const checkBoxUnknown = newField.querySelector('.date-unknown > input');
    const checkBoxCanceled = newField.querySelector('.date-canceled > input');
    const dateBox = newField.querySelector('input[type=date]');

    checkBoxUnknown.addEventListener('change', () => {
        if (checkBoxUnknown.checked) {
            checkBoxCanceled.checked = false;
            dateBox.value = '8888-01-01';
            dateBox.disabled = true;
        } else if (!checkBoxCanceled.checked && !checkBoxUnknown.checked) {
            dateBox.disabled = false;
            dateBox.value = '';
        }
        validateGameReleaseDate();
    });

    checkBoxCanceled.addEventListener('change', () => {
        if (checkBoxCanceled.checked) {
            checkBoxUnknown.checked = false;
            dateBox.value = '9999-01-01';
            dateBox.disabled = true;
        } else if (!checkBoxCanceled.checked && !checkBoxUnknown.checked) {
            dateBox.disabled = false;
            dateBox.value = ''
        }
        validateGameReleaseDate();

    });
};
const createMultiSelectDates = () => {
    const names = document.querySelectorAll('.date-platform-name');
    const dates = document.querySelectorAll('.date-platform-value');
    const namesSelect = document.getElementById("platformName");
    const datesSelect = document.getElementById("releaseDate");
    for (let i = 0; i < names.length; i++) {
        let platformName = document.createElement('option');
        platformName.selected = true;
        platformName.value = names[i].textContent;
        namesSelect.appendChild(platformName);
        let datesOption = document.createElement('option');
        datesOption.selected = true;
        datesOption.value = dates[i].value;
        datesSelect.appendChild(datesOption);
    }

}


