package asd.ecommercenew;

import asd.ecommercenew.dto.request.*;
import asd.ecommercenew.dto.response.PagingDto;
import asd.ecommercenew.dto.response.ProductDtoResponse;
import asd.ecommercenew.service.PaymentService;
import asd.ecommercenew.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@SpringBootTest
class EcommerceNewApplicationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;


    @Test
    void payments() throws IOException {
        //add product
        ProductDtoRequest productDtoRequest = addProduct1();
        productService.save(productDtoRequest);
        //user buy product
        PagingDto<ProductDtoResponse> productResponse = productService.searchProductName("Garmin Venu 2, GPS Smartwatch with Advanced Health Monitoring and Fitness Features, Slate Bezel with Black Case and Silicone Band , 27.9 mm", PageRequest.of(0, 1));
        ProductDtoResponse product = productResponse.getContent().get(0);
        OrderLineRequest orderLineRequest = new OrderLineRequest();
        orderLineRequest.setProductId(product.getId());
        orderLineRequest.setQuantity(1);
        productService.addToCart(orderLineRequest);

        //user place order
        PaymentDtoRequest paymentDtoRequest = new PaymentDtoRequest();
        PaymentTypeDtoRequest paymentTypeDtoRequest = new PaymentTypeDtoRequest("123", "24/12/2022", 123, "address");
        paymentDtoRequest.setPaymentTypeDtoRequest(paymentTypeDtoRequest);
        paymentDtoRequest.setShippingAddress("shipping address");
//        paymentService.payment(paymentDtoRequest);
        System.out.println();
    }

    private ProductDtoRequest addProduct1() throws IOException {
        ProductDtoRequest productDtoRequest = new ProductDtoRequest();
        productDtoRequest.setCategories("Watch");
        productDtoRequest.setModel("Venu 2");
        productDtoRequest.setPrice(269.99);
        productDtoRequest.setProductName("Garmin Venu 2, GPS Smartwatch with Advanced Health Monitoring and Fitness Features, Slate Bezel with Black Case and Silicone Band , 27.9 mm");
        productDtoRequest.setRemainQuantity(100);
        productDtoRequest.setDescription("Suit your style with a smartwatch that's available in two colors and features a bright AMOLED display.GPS mode: 19 hours. GPS plus music mode: 7 hours.Supported Application:GPS. Connectivity technology:GPS,Wi-Fi,Bluetooth. Wireless comm standard:Bluetooth\n" +
                "Get an uninterrupted picture of your health, thanks to battery life of up to 11 days in smartwatch mode\n" +
                "Get up to 8 hours of battery life in GPS mode with music, plus rapid recharging and battery saver mode\n" +
                "Use preloaded workouts that include cardio, yoga, strength, HIIT and Pilates, create your own in the Garmin Connect app on your compatible smartphone, or try Garmin Coach free adaptive training plans to help you prepare for your next running challenge\n" +
                "Download up to 650 songs to your watch, including playlists from Spotify, Amazon Music or Deezer (may require a premium subscription), and connect with wireless headphones (not included) for phone-free listening\n" +
                "With a supported country and payment network, leave your cash and cards at home; Garmin Pay contactless payments let you pay for purchases on the go\n" +
                "Never miss a call, text or social media alert with smart notifications delivered right to your wrist — when paired with a compatible Android or Apple smartphone");
        List<FileDtoRequest> files = new ArrayList<>();
        FileDtoRequest image1 = new FileDtoRequest();
        image1.setBase64(parseImageToBase64("C:\\Users\\Trung\\Desktop\\ProjectCS489\\images\\61jecdXQiWS._AC_SL1500_.jpg"));
        files.add(image1);
        productDtoRequest.setImages(files);
        return productDtoRequest;
    }

    public String parseImageToBase64(String imagePath) throws IOException {
        File imageFile = new File(imagePath);

        // Check if the file exists
        if (!imageFile.exists()) {
            throw new IllegalArgumentException("Image file not found");
        }

        // Read the image file and convert it to a Base64-encoded string
        byte[] fileContent = Files.readAllBytes(imageFile.toPath());
        String base64Image = Base64.getEncoder().encodeToString(fileContent);

        return base64Image;
    }
}
