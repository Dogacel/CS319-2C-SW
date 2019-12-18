package SevenWonders;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SoundManager {
    private static SoundManager managerInstance = null;

    Map<String, Media> soundMap;

    MediaPlayer menuMusic;
    MediaPlayer battleMusic;
    MediaPlayer ageOne;
    MediaPlayer cardSound;
    MediaPlayer discardSound;
    MediaPlayer wonderSound;


    public void initialize(){

        soundMap = new HashMap<>();
        loadSounds();
        menuMusic = new MediaPlayer(soundMap.get("menu"));
        ageOne = new MediaPlayer(soundMap.get("age_one"));
        battleMusic = new MediaPlayer(soundMap.get("battles"));
        discardSound = new MediaPlayer(soundMap.get("discard"));
        wonderSound = new MediaPlayer(soundMap.get("wonder_upgrade"));
    }

    public static SoundManager getInstance() {
        if ( managerInstance == null) {
            managerInstance = new SoundManager();
            managerInstance.initialize();
        }
        return managerInstance;
    }

    private void loadSounds() {
        URL soundResourcesURL = getClass().getClassLoader().getResource("sounds");
        assert soundResourcesURL != null;
        File dir = new File(soundResourcesURL.getPath());

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if (f.getName().endsWith(".mp3")) {
                String type = "";
                if (f.getName().equals("red_card.mp3"))
                    type = "red";
                else if (f.getName().equals("blue_card.mp3"))
                    type = "blue";
                else if (f.getName().equals("yellow_card.mp3"))
                    type = "yellow";
                else if (f.getName().equals("green_card.mp3"))
                    type = "green";
                else if (f.getName().equals("brown_card.mp3"))
                    type = "brown";
                else if (f.getName().equals("papyrus.mp3"))
                    type = "papyrus";
                else if (f.getName().equals("glass.mp3"))
                    type = "glass";
                else if (f.getName().equals("loom.mp3"))
                    type = "loom";
                else if (f.getName().equals("discard.mp3"))
                    type = "discard";
                else if (f.getName().equals("wonder_upgrade.mp3"))
                    type = "wonder_upgrade";
                else if (f.getName().equals("battles.mp3"))
                    type = "battles";
                else if (f.getName().equals("menu_music.mp3"))
                    type = "menu";
                else if (f.getName().equals("age_one.mp3"))
                    type = "age_one";

                Media sound = new Media(f.toURI().toString());

                soundMap.put(type, sound);
            }
        }

    }
    public void playMenuMusic(){
        menuMusic.play();
        menuMusic.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void stopMenuMusic(){ menuMusic.stop(); }

    public void playCardSound(String type)
    {
        cardSound = new MediaPlayer(soundMap.get(type));
        cardSound.play();
    }

    public void playDiscardSound(){ discardSound.play(); discardSound.seek(Duration.ZERO); }

    public void playWonderSound(){
        wonderSound.play();
        wonderSound.seek(Duration.ZERO);
    }

    public void playBattleSound(){
        battleMusic.play();
        battleMusic.seek(Duration.ZERO);
    }

    public void playAgeOneMusic(){
        ageOne.play();
        ageOne.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void stopAgeOneMusic(){
        ageOne.stop();
    }

    public void playAgeTwoMusic(){

    }

    public void stopAgeTwoMusic(){

    }

    public void playAgeThreeMusic(){

    }

    public void stopAgeThreeMusic(){

    }

}
