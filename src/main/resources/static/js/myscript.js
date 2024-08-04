
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

// Avviso elimina ticket
function confirmDelete() {
	return confirm('Sei sicuro di voler eliminare questo ticket? Questa azione non può essere annullata.');
}

// Descrizione a comparsa della nota
document.addEventListener('DOMContentLoaded', function() {
	var noteModal = document.getElementById('noteModal');
	noteModal.addEventListener('show.bs.modal', function(event) {
		var button = event.relatedTarget;
		var description = button.getAttribute('data-note-description');
		var modalBody = noteModal.querySelector('.modal-body #noteDescription');
		modalBody.textContent = description;
	});

	var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
	var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
		return new bootstrap.Tooltip(tooltipTriggerEl)
	})
});

// Avviso prima di eliminare la nota
function deleteNote(noteId) {
	if (confirm('Sei sicuro di voler eliminare questa nota?')) {
		window.location.href = '/note/elimina_nota/' + noteId;
	}
}