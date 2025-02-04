Toastify.defaults.gravity = "toastify-bottom";

function generateLink(e) {
	e.preventDefault();
    $.post("/", { url: $("input#url").val()}).done(function (fragment) {
        $("#result").replaceWith(fragment);
    });
}

function copyText(element) {
	var text = $(element).prev().text();
	navigator.clipboard.writeText(text);
	showAlert("Link copied to clipboard! 📋");
}

function showAlert(text) {
	if(text.length > 0) {
		var toast = Toastify({
			text: text,
			onClick: function() {
				toast.hideToast();
			}
		}).showToast();
	}
}
