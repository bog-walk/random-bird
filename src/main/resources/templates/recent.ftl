<#-- @ftlvariable name="photos" type="kotlin.collections.List<dev.bogwalk.models.UnsplashPhoto>" -->
<#import "_layout.ftl" as layout />
<@layout.wrapper>
    <div class="triple">
        <#list photos as photo>
            <figure>
                <img src="${photo.urls.regular}" alt="${photo.alt}">
                <figcaption><a href="${photo.user.attribution}" target="_blank">${photo.user.name}</a></figcaption>
            </figure>
        </#list>
    </div>
    <p><a href="/">MAIN PAGE</a> &#129370;</p>
</@layout.wrapper>