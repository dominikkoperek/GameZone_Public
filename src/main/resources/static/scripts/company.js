    let gameListProducer = document.getElementById('content-list-producer')
    let producerButton = document.getElementById('producer-button')
    let gameListPublisher = document.getElementById('content-list-publisher')
    let publisherButton = document.getElementById('publisher-button')
function showAllProducedGames() {
    if(gameListProducer.classList.contains('content-list-hide')){
        gameListProducer.classList.remove('content-list-hide')
        gameListProducer.classList.add('content-list-show')
        producerButton.innerText='Ukryj wszystkie wyprodukowane'
    }else {
        gameListProducer.classList.remove('content-list-show')
        gameListProducer.classList.add('content-list-hide')
        producerButton.innerText='Pokaż wszystkie wyprodukowane'
    }
}
    function showAllPublishedGames() {
        if(gameListPublisher.classList.contains('content-list-hide')){
            gameListPublisher.classList.remove('content-list-hide')
            gameListPublisher.classList.add('content-list-show')
            publisherButton.innerText='Ukryj wszystkie wydane'
        }else {
            gameListPublisher.classList.remove('content-list-show')
            gameListPublisher.classList.add('content-list-hide')
            publisherButton.innerText='Pokaż wszystkie wydane'
        }
    }