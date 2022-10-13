<#macro wrapper>
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <meta charset="UTF-8">
            <title>The Daily Bird</title>
            <link rel="stylesheet" href="/static/css/styles.css">
        </head>
        <body>
        <div class="mainContainer">
            <header>
                <h1>The Daily Bird</h1>
            </header>
            <div class="main">
                <#nested>
            </div>
            <footer>
                <p>All photos sourced through Upsplash API</p>
            </footer>
        </div>
        </body>
    </html>
</#macro>