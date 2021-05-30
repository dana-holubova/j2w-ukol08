package cz.czechitas.java2webapps.ukol8.controller;

import cz.czechitas.java2webapps.ukol8.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PostController {

    public final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Metoda, která vrací seznam příspěvků
     */

//    TODO: Nevím, jak použít PageRequest.of(0, 2); počet příspěvků na stránku jsem nastavila v @PageableDefault

    /**
     * Odpověď koučky: Takhle je já myslím v pohodě. Jinak bys prostě vytvořila Pageable page20 = PageRequest.of(0, 20); a
     * ten pak použila jako parametr. Lépe by bylo nastavit to až v servise metodě list(), da se tam přidat i ten
     * sorting: PageRequest.of(0, 20, Sort.by("published").descending());
     */
    @GetMapping("/")
    public ModelAndView postList(@PageableDefault(size = 20) Pageable pageable) {
        return new ModelAndView("index")
                .addObject("posts", postService.list(pageable));
    }

    /**
     * Metoda, která vrací jeden příspěvek
     */

    @GetMapping("/post/{slug}")
    public ModelAndView post(@PathVariable String slug) {
        return new ModelAndView("post")
                .addObject("post", postService.singlePost(slug));
    }

    /**
     * Získání aktuální URL s query parametry bez parametrů {@code size} a {@code page}.
     * <p>
     * Je to ošklivé, ale dělá to, co potřebuju…
     */
    @ModelAttribute("currentURL")
    public String currentURL(HttpServletRequest request) {
        UrlPathHelper helper = new UrlPathHelper();
        return UriComponentsBuilder
                .newInstance()
                .path(helper.getOriginatingRequestUri(request))
                .query(helper.getOriginatingQueryString(request))
                .replaceQueryParam("size")
                .replaceQueryParam("page")
                .build()
                .toString();
    }

}
