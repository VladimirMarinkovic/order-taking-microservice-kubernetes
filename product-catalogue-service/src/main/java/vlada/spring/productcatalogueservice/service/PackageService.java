package vlada.spring.productcatalogueservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import vlada.spring.productcatalogueservice.dto.PackageAtributeDto;
import vlada.spring.productcatalogueservice.dto.PackageDto;
import vlada.spring.productcatalogueservice.dto.ProductDto;
import vlada.spring.productcatalogueservice.model.Package;
import vlada.spring.productcatalogueservice.model.PackageAtribute;
import vlada.spring.productcatalogueservice.model.Product;
import vlada.spring.productcatalogueservice.repository.PackageRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;


    @Cacheable(value = "allPackages")
    public List<PackageDto> findAllPackages() {
        List<Package> allPackages = packageRepository.findAll();
        return allPackages.stream()
                .map(this::mapPackageToPackageDto)
                .collect(Collectors.toList());

    }

    public PackageDto mapPackageToPackageDto(Package p) {
        return PackageDto.builder()
//                .productName(p.getProduct().getProductName())
                .productId(p.getProduct().getId().toString())
                .packageId(p.getId().toString())
                .packageName(p.getPackageName())
                .packageAtributes(
                        p.getPackageAtributes().stream()
                                .map(this::mapPackageAtributeToDto)
                                .collect(Collectors.toList()))
                .build();
    }

    public PackageAtributeDto mapPackageAtributeToDto(PackageAtribute packageAtribute) {
        return PackageAtributeDto.builder()
                .packageAtributeName(packageAtribute.getPackageAtributeName())
                .packageAtributeValue(packageAtribute.getPackageAtributeValue())
                .build();
    }




    public PackageDto findOnePackage(String id) {
        Optional<Package> optionalPackage = packageRepository.findById(Long.parseLong(id));
        Package p = optionalPackage.orElseThrow(IllegalArgumentException::new);
        return mapPackageToPackageDto(p);
    }



    public PackageDto findOnePackageByName(String name) {
        Optional<Package> optionalProduct = packageRepository.findByPackageName(name);
        Package p = optionalProduct.orElseThrow(IllegalArgumentException::new);
        return mapPackageToPackageDto(p);
    }



}
