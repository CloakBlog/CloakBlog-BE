document.getElementById('postForm').addEventListener('submit', function(e) {
    e.preventDefault();

    var formData = new FormData(this);
    fetch('/post/post', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (response.redirected) {
            window.location.href = response.url;
        } else {
            alert("Error")
        }
    })
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));
});
