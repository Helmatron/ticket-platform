
// MONKEY ROTATION NABAR
document.addEventListener('DOMContentLoaded', function() {
    const monkeyButton = document.getElementById('monkeyButton');
    const monkeySymbols = ['&#128584;', '&#128585;', '&#128586;'];
    let currentIndex = 0;

    monkeyButton.addEventListener('click', function() {
        currentIndex = (currentIndex + 1) % monkeySymbols.length;
        monkeyButton.innerHTML = monkeySymbols[currentIndex];
    });
});