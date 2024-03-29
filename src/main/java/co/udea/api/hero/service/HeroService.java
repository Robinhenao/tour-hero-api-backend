package co.udea.api.hero.service;

import co.udea.api.hero.exception.BusinessException;
import co.udea.api.hero.model.Hero;
import co.udea.api.hero.repository.HeroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class HeroService {

    private final Logger log = LoggerFactory.getLogger(HeroService.class);

    private HeroRepository heroRepository;

    public HeroService(HeroRepository heroRepository){
        this.heroRepository = heroRepository;
    }



    public Hero getHero(Integer id){
        Optional<Hero> optionalHero = heroRepository.findById(id);
        if(!optionalHero.isPresent()){
            log.info("No se encuentra un heroe con ID: "+id);
            throw new BusinessException("El heroe no existe");
        }
        return optionalHero.get();
    }

    public List<Hero> listHeroes() {
        return heroRepository.findAll();
    }

    public List<Hero> searchHeroe(String term){
        return heroRepository.findByNameContaining(term);
    }

    public void deleteHeroe(Integer id) {
        if (!heroRepository.existsById(id)) {
            throw new BusinessException("El héroe no existe");
        }
        heroRepository.deleteById(id);
    }

    public Hero addheroe(Hero hero){
        return heroRepository.save(hero);
    }

    public void updateheroe(Hero hero){
        heroRepository.findById(hero.getId()).ifPresent(hero1 -> {
            hero1.setName(hero.getName());
            heroRepository.save(hero1);
        });
    }

}

