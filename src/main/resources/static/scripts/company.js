    let gameListProducer = document.getElementById('games-list-producer')
    let producerButton = document.getElementById('producer-button')
    let gameListPublisher = document.getElementById('games-list-publisher')
    let publisherButton = document.getElementById('publisher-button')
function showAllProducedGames() {
    if(gameListProducer.classList.contains('games-list-hide')){
        gameListProducer.classList.remove('games-list-hide')
        gameListProducer.classList.add('games-list-show')
        producerButton.innerText='Ukryj wszystkie wyprodukowane'
    }else {
        gameListProducer.classList.remove('games-list-show')
        gameListProducer.classList.add('games-list-hide')
        producerButton.innerText='Pokaż wszystkie wyprodukowane'
    }
}
    function showAllPublishedGames() {
        if(gameListPublisher.classList.contains('games-list-hide')){
            gameListPublisher.classList.remove('games-list-hide')
            gameListPublisher.classList.add('games-list-show')
            publisherButton.innerText='Ukryj wszystkie wydane'
        }else {
            gameListPublisher.classList.remove('games-list-show')
            gameListPublisher.classList.add('games-list-hide')
            publisherButton.innerText='Pokaż wszystkie wydane'
        }
    }