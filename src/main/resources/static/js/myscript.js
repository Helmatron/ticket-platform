
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

// SELETTORE OPERATORI
function updateOperator(element) {
	document.getElementById('operatorNameInput').value = element.getAttribute('data-name');
	document.getElementById('operatorIdInput').value = element.getAttribute('data-id');
}

// RESET FORM E VAI A HOME
function resetFormAdminHome() {
	window.location.href = "/admin/index";
}

// Disabilita l'invio del form se ci sono campi non validi
(function() {
	'use strict'

	var forms = document.querySelectorAll('.needs-validation')

	Array.prototype.slice.call(forms)
		.forEach(function(form) {
			form.addEventListener('submit', function(event) {
				if (!form.checkValidity()) {
					event.preventDefault()
					event.stopPropagation()
				}

				form.classList.add('was-validated')
			}, false)
		})
})()