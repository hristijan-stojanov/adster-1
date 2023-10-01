package mk.ukim.finki.wpproject.web.controller.API;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.City;
import mk.ukim.finki.wpproject.model.Comment;
import mk.ukim.finki.wpproject.model.DTO.AdDto;
import mk.ukim.finki.wpproject.model.User;
import mk.ukim.finki.wpproject.model.enums.AdType;
import mk.ukim.finki.wpproject.model.enums.Condition;
import mk.ukim.finki.wpproject.model.enums.Role;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wpproject.service.AdService;
import mk.ukim.finki.wpproject.service.CategoryService;
import mk.ukim.finki.wpproject.service.CityService;
import mk.ukim.finki.wpproject.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiAdController {
    private final AdService adService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final CityService cityService;
    @Autowired
    public ApiAdController(AdService adService, CategoryService categoryService, UserService userService, CityService cityService) {
        this.adService = adService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.cityService = cityService;
    }

    @GetMapping(value = "/ads")
    public List<AdDto> getAd()
    {
       List<Ad> listAd= adService.findAll();
       List<AdDto> listAdDto = new ArrayList<>();
       for(Ad ad : listAd)
       {
           AdDto adDto = new AdDto(ad.getId(),
                   ad.getTitle(),
                   ad.getDescription(),
                   ad.isExchangePossible(),
                   ad.isDeliveryPossible(),
                   ad.getPrice(),
                   ad.getDateCreated(),
                   ad.getType(),
                   ad.getCondition(),
                   ad.getImages(),
                   ad.getCategory().getName(),
                   ad.getCity().getName(),
                   ad.getCity().getLat(),
                   ad.getCity().getLon(),
                   ad.getComments(),
                   Long.valueOf(12345678910L));
           listAdDto.add(adDto);
       }
        return listAdDto;
    }
    @GetMapping("/image/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        Path imagePath = Paths.get("c:/Users/PC/Desktop/adster/src/main/resources/static/images/", imageName);
        byte[] imageBytes;

        try {
            imageBytes = Files.readAllBytes(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
    @GetMapping("/savedAds/{id}")
    public List<AdDto>  getSavedAdd(@PathVariable long id)
    {
        List<Ad> savedAds = userService.findAllSavedAdsByUser(id);
        List<AdDto> listSavedAdDto = new ArrayList<>();
        for(Ad ad : savedAds)
        {
            AdDto adDto = new  AdDto(ad.getId(),
                    ad.getTitle(),
                    ad.getDescription(),
                    ad.isExchangePossible(),
                    ad.isDeliveryPossible(),
                    ad.getPrice(),
                    ad.getDateCreated(),
                    ad.getType(),
                    ad.getCondition(),
                    ad.getImages(),
                    ad.getCategory().getName(),
                    ad.getCity().getName(),
                    ad.getCity().getLat(),
                    ad.getCity().getLon(),
                    ad.getComments(),
                    Long.valueOf(12345678910L));
            listSavedAdDto.add(adDto);
        }
        return listSavedAdDto;

    }
    @GetMapping("/myAds/{id}")
    public List<AdDto> getMyAds(@PathVariable long id)
    {
        List<Ad> myAds = userService.findAllAdvertisedAdsByUser(id);
        List<AdDto> listMyAdsAdDto = new ArrayList<>();
        for(Ad ad : myAds)
        {
            AdDto adDto = new  AdDto(ad.getId(),
                    ad.getTitle(),
                    ad.getDescription(),
                    ad.isExchangePossible(),
                    ad.isDeliveryPossible(),
                    ad.getPrice(),
                    ad.getDateCreated(),
                    ad.getType(),
                    ad.getCondition(),
                    ad.getImages(),
                    ad.getCategory().getName(),
                    ad.getCity().getName(),
                    ad.getCity().getLat(),
                    ad.getCity().getLon(),
                    ad.getComments(),
                    Long.valueOf(12345678910L));
            listMyAdsAdDto.add(adDto);
        }
        return listMyAdsAdDto;
    }
    @GetMapping("/save/{iduser}/{idad}")
    public ResponseEntity<String> saveAd(@PathVariable long iduser,@PathVariable long idad){
        Ad ad = adService.findById(idad).orElseThrow(() -> new AdNotFoundException(idad));

       User  user = userService.findById(iduser).orElse(null);


        if (!user.getSavedAds().contains(ad))
            user.getSavedAds().add(ad);


        if(user !=null) {
            userService.save(user).orElseThrow(RuntimeException::new);
        }
        return ResponseEntity.ok("Status is OK");
    }
    @GetMapping("/delete/{iduser}/{idad}")
    public ResponseEntity<String> deleteAd(@PathVariable long iduser,@PathVariable long idad)
    {
        Ad ad = this.adService.findById(idad).orElseThrow(() -> new AdNotFoundException(idad));

        User user = userService.findById(iduser).orElse(null);;

        ad.getSavedByUsers().stream().forEach(u -> u.getSavedAds().remove(ad));

        user.getAdvertisedAds().remove(ad);
        userService.save(user).orElseThrow(RuntimeException::new);
        return ResponseEntity.ok("Status is OK");
    }
    @GetMapping("/search")
    public List<AdDto> search(@RequestBody String requestBody)
    {

        try{
            System.out.println(requestBody);
            JSONObject jsonObject = new JSONObject(requestBody);
            Long id = null;
            String type = jsonObject.getString("selectedAdType");
            String title = jsonObject.getString("title");
            String cityName = jsonObject.getString("selectedLocation");
           /// String categoryId = jsonObject.getString("categoryId");
            String priceFrom = jsonObject.getString("priceFrom");
            String priceTo = jsonObject.getString("priceTo");
            List<Ad> myAds = adService.filterList(AdType.Selling,title,"Strumica",Long.parseLong("1"),Double.parseDouble(priceFrom)
            ,Double.parseDouble(priceTo));
            List<AdDto> listMyAdsAdDto = new ArrayList<>();
            for(Ad ad : myAds)
            {
                AdDto adDto = new  AdDto(ad.getId(),
                        ad.getTitle(),
                        ad.getDescription(),
                        ad.isExchangePossible(),
                        ad.isDeliveryPossible(),
                        ad.getPrice(),
                        ad.getDateCreated(),
                        ad.getType(),
                        ad.getCondition(),
                        ad.getImages(),
                        ad.getCategory().getName(),
                        ad.getCity().getName(),
                        ad.getCity().getLat(),
                        ad.getCity().getLon(),
                        ad.getComments(),
                        Long.valueOf(12345678910L));
                listMyAdsAdDto.add(adDto);
            }
            return  listMyAdsAdDto;


        }
        catch (InvalidUserCredentialsException | JSONException exception) {
            System.out.println("InvalidUserCredentialsException");

        }
        return null;
    }





}
