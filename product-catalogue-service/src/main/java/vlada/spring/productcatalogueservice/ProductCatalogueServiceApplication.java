package vlada.spring.productcatalogueservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import vlada.spring.productcatalogueservice.model.Package;
import vlada.spring.productcatalogueservice.model.PackageAtribute;
import vlada.spring.productcatalogueservice.model.Product;
import vlada.spring.productcatalogueservice.repository.PackageAtributeRepository;
import vlada.spring.productcatalogueservice.repository.PackageRepository;
import vlada.spring.productcatalogueservice.repository.ProductRepository;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class ProductCatalogueServiceApplication implements CommandLineRunner {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    PackageAtributeRepository packageAtributeRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProductCatalogueServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

       Product product1 =  new Product("Internet");
       Product product2 =  new Product("TV");
       Product product3 =  new Product("Teleponhy");
       Product product4 =  new Product("Mobile");

        List<Product> products = Arrays.asList(product1,product2,product3,product4);
        products.stream().forEach(p -> productRepository.save(p));

        Package p1 =  new Package("Net1", product1);
        Package p2 =  new Package("NetMax", product1);
        Package p3 =  new Package("Tv1", product2);
        Package p4 =  new Package("TvPlus", product2);
        Package p5 =  new Package("TelLight", product3);
        Package p6 =  new Package("TelFull", product3);
        Package p7 =  new Package("Mob1", product4);
        Package p8 =  new Package("Mob2", product4);

        List<Package> packages = Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8);
        packages.stream().forEach(p -> packageRepository.save(p));


        List<PackageAtribute> packageAtributes = Arrays.asList(
                new PackageAtribute("Speed","250Mbps",p1),
                new PackageAtribute("Limit","Unlimited",p1),
                new PackageAtribute("Speed","1Gbps",p2),
                new PackageAtribute("Limit","Unlimited",p2),
                new PackageAtribute("Channels","90",p3),
                new PackageAtribute("Rezolution","4K",p3),
                new PackageAtribute("Channels","140",p4),
                new PackageAtribute("Rezolution","4K",p4),
                new PackageAtribute("Type","Free on net",p5),
                new PackageAtribute("Type","Free Calls",p6),
                new PackageAtribute("Type","Prepaid",p7),
                new PackageAtribute("Type","Postpaid",p8),
                new PackageAtribute("Free Minutes","120",p8)


        );
        packageAtributes.stream().forEach(p -> packageAtributeRepository.save(p));



    }
}
