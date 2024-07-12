let slider = document.getElementById("slider");
let userRate = document.getElementById("user-rate")
let userRateMessage = document.getElementById("user-rate-message");
if (userRate !== null && userRate.innerText !== '?') switchRate();

function switchRate() {
    switch (slider.value) {
        case "0":
        case "0.5":
        case "1":
        case "1.5":
        case "2":
            userRate.innerHTML = slider.value;
            userRateMessage.innerHTML = "tragedia";
            userRate.classList.add("rate-red");
            userRate.classList.remove("rate-orange");
            userRate.classList.remove("rate-green");
            userRate.classList.remove("rate-best");
            break;
        case "2.5":
        case "3":
        case "3.5":
        case "4":
            userRate.innerHTML = slider.value;
            userRateMessage.innerHTML = "bardzo słaba";
            userRate.classList.add("rate-red");
            userRate.classList.remove("rate-orange");
            userRate.classList.remove("rate-green");
            userRate.classList.remove("rate-best");
            break;
        case "4.5":
        case "5":
            userRate.innerHTML = slider.value;
            userRateMessage.innerHTML = "słaba";
            userRate.classList.add("rate-orange");
            userRate.classList.remove("rate-red");
            userRate.classList.remove("rate-green");
            userRate.classList.remove("rate-best");
            break;
        case "5.5":
        case "6":
        case "6.5":
            userRate.innerHTML = slider.value;
            userRateMessage.innerHTML = "przeciętna";
            userRate.classList.add("rate-orange");
            userRate.classList.remove("rate-red");
            userRate.classList.remove("rate-green");
            userRate.classList.remove("rate-best");
            break;
        case "7":
        case "7.5":
            userRate.innerHTML = slider.value;
            userRateMessage.innerHTML = "dobra";
            userRate.classList.add("rate-green");
            userRate.classList.remove("rate-red");
            userRate.classList.remove("rate-orange");
            userRate.classList.remove("rate-best");
            break;
        case "8":
        case "8.5":
            userRate.innerHTML = slider.value;
            userRateMessage.innerHTML = "bardzo dobra";
            userRate.classList.add("rate-green");
            userRate.classList.remove("rate-red");
            userRate.classList.remove("rate-orange");
            userRate.classList.remove("rate-best");
            break;
        case "9":
        case "9.5":
            userRate.innerHTML = slider.value;
            userRateMessage.innerHTML = "świetna";
            userRate.classList.add("rate-green");
            userRate.classList.remove("rate-red");
            userRate.classList.remove("rate-orange");
            userRate.classList.remove("rate-best");
            break;
        case "10":
            userRate.innerHTML = slider.value;
            userRateMessage.innerHTML = "wybitna";
            userRate.classList.add("rate-best");
            userRate.classList.remove("rate-green");
            userRate.classList.remove("rate-red");
            userRate.classList.remove("rate-orange");
            break;
        default:
            userRate.innerHTML = slider.value;
            userRateMessage.innerHTML = "";
            break;
    }
}

if (slider !== null) {
    slider.oninput = switchRate;
}
//HIDE RATES
let closeButton = document.getElementById('close-rates');
let rateStatsSticky = document.getElementById('rate-stats-sticky');
let showStatsButton = document.getElementById('show-stats');
let statsContainer = document.getElementById('stats-container');
let rateBar =document.querySelectorAll(".rate-load-bar");

closeButton.addEventListener('click', () => {
    removeClasses();
})
rateStatsSticky.addEventListener('click', () => {
    removeClasses();
});
statsContainer.addEventListener('click',ev=>{
    ev.stopPropagation();
});

//DISPLAY RATES

if (statsContainer.classList.contains('hide-stats-container')) {
    showStatsButton.addEventListener('click', () => {
        statsContainer.classList.remove("hide-stats-container");
        rateStatsSticky.classList.remove("hide-stats-container");
        statsContainer.classList.add("rates-slide-animation");
        rateStatsSticky.classList.add("rate-opacity-animation");
        rateBar.forEach(c=>c.classList.add("rate-load-bar-animation"));

    });
}
function removeClasses(){
    statsContainer.classList.add("hide-stats-container");
    rateStatsSticky.classList.add("hide-stats-container");
    statsContainer.classList.remove("rates-slide-animation");
    rateStatsSticky.classList.remove("rate-opacity-animation");
    rateBar.forEach(c=>c.classList.remove("rate-load-bar-animation"));
}

