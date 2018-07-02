package com.nguyen.week5challenge;

import com.cloudinary.utils.ObjectUtils;
import com.nguyen.week5challenge.config.CloudinaryConfig;
import com.nguyen.week5challenge.model.Meme;
import com.nguyen.week5challenge.repository.MemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    MemeRepository memeRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listActors(Model model) {
        model.addAttribute("memes", memeRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String newMeme(Model model) {
        model.addAttribute("meme", new Meme());
        return "form";
    }

    @PostMapping("/add")
    public String processMeme(@Valid @ModelAttribute("meme") Meme meme, BindingResult result,
                              @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("Can't process with no photo...");
            //meme.setPictureUrl("/img/placeholder.png");
            return "redirect:/add";
        }
        if (!result.hasErrors()) {
            try {
                Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                meme.setPictureUrl(uploadResult.get("url").toString());

                // Implement the more than 20 characters
                meme.setShortDescription(meme.moreThan20(meme.getDescription()));

                // Set the date to the current time
                LocalDate currDate = LocalDate.now();
                meme.setPostDate(currDate);

                memeRepository.save(meme);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/add";
            }
            System.out.println("There is no error...");
            return "redirect:/";
        }
        else {
            System.out.println("There is an error...");
            return "form";
        }
    }

    @RequestMapping("/detail/{id}")
    public String showMeme(@PathVariable("id") long id, Model model) {
        model.addAttribute("meme", memeRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateMeme(@PathVariable("id") long id, Model model) {
        model.addAttribute("meme", memeRepository.findById(id));
        return "form";
    }

    @RequestMapping("/delete/{id}")
    public String deleteMeme(@PathVariable("id") long id) {
        memeRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/like/{id}")
    public String likePhoto(@PathVariable("id") long id, Meme meme) {
        meme = memeRepository.findById(id).get();
        // increment like by 1 every time the "Like" is pressed
        meme.setLikeCount(meme.getLikeCount() + 1);
        memeRepository.save(meme);
        return "redirect:/";
    }

}
