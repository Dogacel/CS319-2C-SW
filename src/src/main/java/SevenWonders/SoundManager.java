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
    MediaPlayer ageTwo;
    MediaPlayer ageThree;
    MediaPlayer cardSound;
    MediaPlayer discardSound;
    MediaPlayer wonderSound;
    MediaPlayer startTheGame;


    public void initialize(){

        soundMap = new HashMap<>();
        loadSounds();
        menuMusic = new MediaPlayer(soundMap.get("menu"));
        ageOne = new MediaPlayer(soundMap.get("age_one"));
        ageTwo = new MediaPlayer(soundMap.get("age_two"));
        ageThree = new MediaPlayer(soundMap.get("age_three"));
        battleMusic = new MediaPlayer(soundMap.get("battles"));
        discardSound = new MediaPlayer(soundMap.get("discard"));
        wonderSound = new MediaPlayer(soundMap.get("wonder_upgrade"));
        startTheGame = new MediaPlayer(soundMap.get("start"));
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
            if (f.getName().endsWith(".wav")) {
                String type = "";
                if (f.getName().equals("red_card.wav"))
                    type = "red";
                else if (f.getName().equals("blue_card.wav"))
                    type = "blue";
                else if (f.getName().equals("yellow_card.wav"))
                    type = "yellow";
                else if (f.getName().equals("green_card.wav"))
                    type = "green";
                else if (f.getName().equals("brown_card.wav"))
                    type = "brown";
                else if (f.getName().equals("purple_card.wav"))
                    type = "purple";
                else if (f.getName().equals("papyrus.wav"))
                    type = "papyrus";
                else if (f.getName().equals("glass.wav"))
                    type = "glass";
                else if (f.getName().equals("loom.wav"))
                    type = "loom";
                else if (f.getName().equals("discard.wav"))
                    type = "discard";
                else if (f.getName().equals("wonder_upgrade.wav"))
                    type = "wonder_upgrade";
                else if (f.getName().equals("battles.wav"))
                    type = "battles";
                else if (f.getName().equals("start.wav"))
                    type = "start";
                else if (f.getName().equals("menu_music.wav"))
                    type = "menu";
                else if (f.getName().equals("age_one.wav"))
                    type = "age_one";
                else if (f.getName().equals("age_two.wav"))
                    type = "age_two";
                else if (f.getName().equals("age_three.wav"))
                    type = "age_three";


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

    public void playDiscardSound(){
        discardSound.play();
        discardSound.seek(Duration.ZERO);
    }

    public void startTheGameAlready(){
        startTheGame.play();
        startTheGame.seek(Duration.ZERO);
    }

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
        ageTwo.play();
        ageTwo.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void stopAgeTwoMusic(){
        ageTwo.stop();
    }

    public void playAgeThreeMusic(){
        ageThree.play();
        ageThree.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void stopAgeThreeMusic(){
        ageThree.stop();
    }

}
