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
const releaseDate = document.getElementById('release-date');
const releaseDateError = document.getElementById('release-date-error');
let releaseDateValue;
let formDate;
let isReleaseInFuture;
const validateGameReleaseDate = () => {
    releaseDateValue = releaseDate.value;
    let todayDate = Date.now();
    formDate = new Date(releaseDateValue);
    if (formDate.getFullYear() < 1980) {
        releaseDate.classList.add('error-input');
        releaseDateError.innerHTML = 'Rok nie może być wcześniejszy niż 1980';
        return false;
    }
    if (formDate.getFullYear() > 2099) {
        releaseDate.classList.add('error-input');
        releaseDateError.innerHTML = 'Rok nie może być poźniejszy niż 2099';
        return false;
    }

    if (formDate > todayDate) {
        bigSuggestionPosterDisplayContainer.classList.remove("hide-big-suggestion-poster");
        bigSuggestionPosterDisplayContainer.classList.add("show-big-suggestion-poster");
        isReleaseInFuture = true;
    } else {
        bigSuggestionPosterDisplayContainer.classList.add("hide-big-suggestion-poster");
        bigSuggestionPosterDisplayContainer.classList.remove("show-big-suggestion-poster");
        isReleaseInFuture = false;
    }

    releaseDate.classList.remove('error-input');
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
    gamePosterHeightWidth.innerHTML = '';
    gamePosterWidthHeight.innerHTML = '';


    if (gamePoster.value !== "") {
        size = ((gamePoster.files[0].size / 1024) / 1024).toString().slice(0, 4);
        extension = gamePoster.files[0].type;

        const img = new Image();
        img.src = window.URL.createObjectURL(gamePoster.files[0]);
        await new Promise((resolve) => {
            img.onload = () => {
                width = img.width;
                height = img.height;
                resolve();
            };
        });
    }
    let proportionHeightWidth = height / width;
    let slicedProportionHeightWidth = proportionHeightWidth.toString().slice(0, 4);

    let proportionWidthHeight = width / height;
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
    let width;
    let height;
    let proportionHeightWidth;
    let slicedProportionHeightWidth;
    let proportionWidthHeight;
    let slicedProportionWidthHeight;

    bigSuggestionWidthHeight.innerHTML = '';
    bigSuggestionHeightWidth.innerHTML = '';

    if (bigSuggestionPoster.value !== "") {
        size = ((bigSuggestionPoster.files[0].size / 1024) / 1024).toString().slice(0, 4);
        extension = bigSuggestionPoster.files[0].type;

        const img = new Image();
        img.src = window.URL.createObjectURL(bigSuggestionPoster.files[0]);
        await new Promise((resolve) => {
            img.onload = () => {
                width = img.width;
                height = img.height;
                proportionHeightWidth = height / width;
                slicedProportionHeightWidth = proportionHeightWidth.toString().slice(0, 4);

                proportionWidthHeight = width / height;
                slicedProportionWidthHeight = proportionWidthHeight.toString().slice(0, 4);
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
    if ((bigSuggestionPoster.value !== "" && size > 1)) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Obraz jest za duży " + size + "Mb";
        return false;
    }
    if ((bigSuggestionPoster.value !== "" && height > 1200)) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Wysokość obrazu jest za duża " + height + "px" + " (max 700px)";
        return false;
    }
    if ((bigSuggestionPoster.value !== "" && width > 2400)) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Szerokość obrazu jest za duża " + width + "px" + " (max 2200px)";
        return false;
    }
    if ((bigSuggestionPoster.value !== "" && height < 350)) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Wysokość obrazu jest za mała " + height + "px" + " (min 350px)";
        return false;
    }
    if ((bigSuggestionPoster.value !== "" && width < 1100)) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Szerokość obrazu jest za mała " + width + "px" + " (min 1100px)";
        return false;
    }
    if (proportionHeightWidth < 0.31 || proportionHeightWidth > 0.51) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Proporcje wysokośc/szerokość powinna być 0.3-0.5 jest " + slicedProportionHeightWidth;
        return false;
    }
    if (proportionWidthHeight < 2 || proportionWidthHeight > 3.2) {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Proporcje szerokośc/wysokość powinna być 2.0-3.2 jest " + slicedProportionWidthHeight;
        return false;
    }
    if (bigSuggestionPoster.value !== "" && extension !== "image/jpeg" && extension !== "image/png" && extension !== "image/jpg") {
        bigSuggestionPoster.classList.add("error-input");
        bigSuggestionPosterError.innerHTML = "Nieobsługiwany format pliku!";
        removeBigSuggestionPoster();
        return false;
    }

    bigSuggestionHeightWidth.innerHTML = "wysokość/szerokość = " + slicedProportionHeightWidth;
    bigSuggestionWidthHeight.innerHTML = "szerokość/wysokość = " + slicedProportionWidthHeight;

    bigSuggestionPoster.classList.remove("error-input");
    bigSuggestionPosterError.innerHTML = "";
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
    let bigPoster;
    if (date) {
        bigPoster = await validateBigSuggestionPoster();
    }
    return gameTitle && gameShortDescription && gameLongDescription && trailerId && producer && publisher &&
        date && gameModes && playerRange && platforms && poster && bigPoster && categories;
}

function sendForm() {
    validateGameForm().then(result => {
        if (result) {
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
        }, 500)
    }).catch(error => {
        console.error('Wystąpił błąd podczas walidacji', error)
    });
}

//IMAGE PREVIEW
function previewBeforeUpload(id) {
    document.querySelector("#" + id).addEventListener("change", function (e) {
        let file = e.target.files[0];
        let url = URL.createObjectURL(file);
        if (file.type === "image/jpeg" || file.type === "image/png" || file.type === "image/jpg") {
            document.querySelector("#" + id + "-preview div").innerText = file.name;
            document.querySelector("#" + id + "-preview img").src = url;
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
}

bigSuggestionPoster.addEventListener("input", function () {
    previewBeforeUpload("big-suggestion-poster");
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

    releaseDate.classList.remove("error-input");
    releaseDateError.innerHTML = '';

    categories.classList.remove("error-input");
    categoriesError.innerHTML = '';

    showPlayersRange.classList.add("hide-players-range");
    showPlayersRange.classList.remove("show-players-range");

    gameModesContainer.classList.remove("error-input");
    gameModesError.innerHTML = '';

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

    isReleaseInFuture = false;
    removeGamePoster();
    removeBigSuggestionPoster();
}

//RESET ERRORS MESSAGE WHEN PAGE LOAD
function resetErrorMessages() {
    categoriesError.innerHTML = '';
}

