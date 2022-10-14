<#-- @ftlvariable name="photo" type="dev.bogwalk.models.UnsplashPhoto" -->
<#import "_layout.ftl" as layout />
<@layout.wrapper>
    <div>
        <figure>
            <img src="${photo.urls.regular}" alt="${photo.alt}">
            <figcaption><a href="${photo.user.attribution}" target="_blank">${photo.user.name}</a></figcaption>
        </figure>
    </div>
    <div>
        <p><a href="/random">ANOTHER</a> &#128038;</p>
        <p><a href="/">MAIN PAGE</a> &#129370;</p>
    </div>
</@layout.wrapper>