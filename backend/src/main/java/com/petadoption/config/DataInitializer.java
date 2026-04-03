package com.petadoption.config;

import com.petadoption.model.Pet;
import com.petadoption.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Data Initializer
 * Populates the database with sample pet data on startup
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PetRepository petRepository;

    @Override
    public void run(String... args) throws Exception {
        if (petRepository.count() == 0) {
            System.out.println("[INFO] Initializing sample pet data...");

            Pet max = Pet.builder()
                    .name("Max")
                    .type(Pet.PetType.DOG)
                    .breed("Golden Retriever")
                    .description("Friendly and energetic, loves to play fetch!")
                    .age(3)
                    .gender(Pet.Gender.MALE)
                    .imageUrl("https://images.unsplash.com/photo-1517849845537-4d257902454a?auto=format&fit=crop&w=800&q=80")
                    .status(Pet.AdoptionStatus.AVAILABLE)
                    .isVaccinated(true)
                    .isNeutered(true)
                    .healthNotes("Healthy and active")
                    .build();

            Pet luna = Pet.builder()
                    .name("Luna")
                    .type(Pet.PetType.CAT)
                    .breed("Siamese Mix")
                    .description("Elegant and affectionate, perfect companion")
                    .age(2)
                    .gender(Pet.Gender.FEMALE)
                    .imageUrl("https://images.unsplash.com/photo-1518791841217-8f162f1e1131?auto=format&fit=crop&w=800&q=80")
                    .status(Pet.AdoptionStatus.AVAILABLE)
                    .isVaccinated(true)
                    .isNeutered(true)
                    .healthNotes("Very friendly with other pets")
                    .build();

            Pet rocky = Pet.builder()
                    .name("Rocky")
                    .type(Pet.PetType.DOG)
                    .breed("Husky")
                    .description("Adventurous spirit, loves outdoor activities")
                    .age(1)
                    .gender(Pet.Gender.MALE)
                    .imageUrl("https://images.unsplash.com/photo-1605568427561-40dd23c2acea?auto=format&fit=crop&w=800&q=80")
                    .status(Pet.AdoptionStatus.AVAILABLE)
                    .isVaccinated(true)
                    .isNeutered(false)
                    .healthNotes("Needs regular exercise")
                    .build();

            Pet bella = Pet.builder()
                    .name("Bella")
                    .type(Pet.PetType.RABBIT)
                    .breed("Holland Lop")
                    .description("Gentle and quiet, great for apartments")
                    .age(1)
                    .gender(Pet.Gender.FEMALE)
                    .imageUrl("https://images.unsplash.com/photo-1543852786-1cf6624b9987?auto=format&fit=crop&w=800&q=80")
                    .status(Pet.AdoptionStatus.AVAILABLE)
                    .isVaccinated(false)
                    .isNeutered(false)
                    .healthNotes("Loves carrots and hay")
                    .build();

            Pet whiskers = Pet.builder()
                    .name("Whiskers")
                    .type(Pet.PetType.CAT)
                    .breed("Orange Tabby")
                    .description("Playful and curious, keeps you entertained")
                    .age(4)
                    .gender(Pet.Gender.MALE)
                    .imageUrl("https://images.unsplash.com/photo-1573865526739-10659fec78a5?auto=format&fit=crop&w=800&q=80")
                    .status(Pet.AdoptionStatus.AVAILABLE)
                    .isVaccinated(true)
                    .isNeutered(true)
                    .healthNotes("Very vocal and affectionate")
                    .build();

            Pet polly = Pet.builder()
                    .name("Polly")
                    .type(Pet.PetType.BIRD)
                    .breed("Parrot")
                    .description("Talkative and vibrant, full of personality")
                    .age(5)
                    .gender(Pet.Gender.FEMALE)
                    .imageUrl("https://images.unsplash.com/photo-1548199973-03cce0bbc87b?auto=format&fit=crop&w=800&q=80")
                    .status(Pet.AdoptionStatus.AVAILABLE)
                    .isVaccinated(false)
                    .isNeutered(false)
                    .healthNotes("Knows several words")
                    .build();

            petRepository.saveAll(
                    Arrays.asList(max, luna, rocky, bella, whiskers, polly)
            );

            System.out.println("[INFO] Sample pet data initialized successfully");
        }
    }
}