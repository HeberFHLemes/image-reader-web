// Function to update the textarea inner html
// with the text read from the image uploaded
/*function updateResponseFragment(){

    document.getElementById('inputForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(this);

        fetch('/upload', {
            method: 'POST',
            body: formData,
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            }
        }).then(response => response.text()).then(html => {
            document.getElementById('responseTextArea').innerHTML = html;
        });
    });
}*/