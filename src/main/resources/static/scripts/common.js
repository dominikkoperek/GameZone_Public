const toggle = document.querySelector(".menu-toggle");
const menu = document.querySelector(".menu");
const notificationElement = document.querySelectorAll(".notification-bar");

function toggleMenu() {
    if (menu.classList.contains("expanded")) {
        menu.classList.remove("expanded");
        menu.classList.add("hided");
        toggle.querySelector('a').innerHTML = '<i id="toggle-icon" class="fa-solid fa-bars hide"></i>';
    } else {
        menu.classList.add("expanded");
        menu.classList.remove("hided");
        toggle.querySelector('a').innerHTML = '<i id="toggle-icon" class="fa-solid fa-bars show"></i>';
    }
}

function notificationBar() {
    notificationElement.forEach(function (notificationElement) {
        if (notificationElement) {
            setTimeout(function () {
                notificationElement.style.opacity = '0';
                notificationElement.style.transition = 'opacity 200ms';
            }, 7000);

            setTimeout(function () {
                notificationElement.style.height = '0';
                notificationElement.style.padding = '0';
                notificationElement.style.transition = 'all 200ms';
            }, 7050);
        }
    });
}

function showYear() {
    let currentYear = new Date().getFullYear();
    document.getElementById("copyright").innerHTML += " " + currentYear + " &copy; GameZone";
}

toggle.addEventListener("click", toggleMenu, false);

window.onload = function () {
    showYear();
    notificationBar();
};


