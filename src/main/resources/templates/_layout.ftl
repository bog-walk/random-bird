<#macro wrapper>
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <meta charset="UTF-8">
            <title>Random &#128038;</title>
            <link rel="stylesheet" href="/styles.css">
        </head>
        <body>
        <div class="mainContainer">
            <header>
                <h1>Birdwatching Made Easy</h1>
            </header>
            <div class="main">
                <#nested>
            </div>
            <footer>
                <p>All photos hosted by <a href="https://unsplash.com/" target="_blank">Unsplash</a></p>
            </footer>
        </div>
        </body>
    </html>
</#macro>