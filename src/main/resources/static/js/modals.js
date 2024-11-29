document.addEventListener("DOMContentLoaded", function() {
    if (document.getElementById('errorModal')) {
        var errorModal = new bootstrap.Modal(document.getElementById('errorModal'), {
            backdrop: 'static',
            keyboard: false
        });
        errorModal.show();
    }
});