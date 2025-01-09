setInterval(() => {
    fetch('http://127.0.0.1:1080/check')
    .then(response => response.json())
    .then(json => {
        if(json.status === 'UPDATE')
        { 
            fetch('http://127.0.0.1:1080/reset')
            .then(response => response.json())
            .then(() => location.reload())
            .catch(console.error);
        }
    })
    .catch(console.error);
}, 1000);