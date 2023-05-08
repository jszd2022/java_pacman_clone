package gameCharacters;

import gameMechanics.Rules;
import gameMechanics.StaticData;
import gameTypes.Direction;
import gameTypes.Position;
import gameTypes.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class PacMan implements GeneralCharacter{
    private Sprite[] sprites;
    private Position pixelPosition;
    private Position matrixPosition;
    private int selectedSpriteSet;
    private Direction direction;
    private boolean alive;
    private boolean livesDecremented;
    private boolean powerUp;
    private int lives;
    boolean invulnerable;

    public PacMan(Rules r, Position startingPoint) throws Exception {

        this.sprites = new Sprite[5];

        BufferedImage[] tmp = new BufferedImage[4];
        BufferedImage buffer = ImageIO.read(
                new File("spriteImages/"+r.getTheme()+".png")
        );
        int spriteSize = StaticData.spriteSize;

        for(int j = 0; j < 4; j++) {
            for(int i = 0; i < 4; i++) {
                tmp[i] = buffer.getSubimage(
                        j * spriteSize + StaticData.pacManSpriteOffsetX,
                        i * spriteSize + StaticData.pacManSpriteOffsetY,
                        spriteSize, spriteSize
                );
            }
            this.sprites[j] = new Sprite(tmp);
        }
        for(int i = 0; i < 4; i++) {
            tmp[i] = buffer.getSubimage(
                    StaticData.eatenSpriteOffsetX + spriteSize,
                    i * spriteSize + StaticData.blueSpriteOffsetY,
                    spriteSize, spriteSize
            );
        }
        this.sprites[4] = new Sprite(tmp);

        this.pixelPosition = new Position(
                r.getCellDistance() * startingPoint.getX(),
                r.getCellDistance() * startingPoint.getY()
        );
        this.matrixPosition = new Position(
                startingPoint.getX(),
                startingPoint.getY()
        );
        alive = true;
        lives = StaticData.pacManStartingLives;
        invulnerable = false;
        livesDecremented = false;
        direction = Direction.LEFT;
    }
    @Override
    public void movePixelPosition(Direction d, int distance) {
        pixelPosition.addToX(d.getX()*distance);
        pixelPosition.addToY(d.getY()*distance);
    }

    @Override
    public void moveMatrixPosition(Direction d) {
        matrixPosition.addToX(d.getX());
        matrixPosition.addToY(d.getY());
    }

    @Override
    public Position getPixelPosition() {
        return pixelPosition;
    }

    @Override
    public void setPixelPosition(Position pixelPosition) {
        this.pixelPosition = pixelPosition;
    }

    @Override
    public Position getMatrixPosition() {
        return matrixPosition;
    }

    @Override
    public void setMatrixPosition(Position matrixPosition) {
        this.matrixPosition = matrixPosition;
    }

    @Override
    public Sprite getSpriteSet() {
        if(this.isAlive())
            return sprites[selectedSpriteSet];
        return sprites[4];
    }

    @Override
    public void updateSpriteDirection() {
        selectedSpriteSet = direction.getId();
    }

    @Override
    public boolean isAlive() {
        return alive;
    }
    @Override
    public void decrementLives() {
        if(!invulnerable) {
            lives--;
            invulnerable = true;
            livesDecremented = true;
        }
        if(lives < 1){
            setAlive(false);
        }
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isPowerUp() {
        return powerUp;
    }

    public void setPowerUp(boolean powerUp) {
        this.powerUp = powerUp;
    }
    public void resetInvulnerable() {
        invulnerable = false;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public int getLives() {
        return lives;
    }

    public boolean isLivesDecrementedAndReset() {
        if(livesDecremented) {
            livesDecremented = false;
            return true;
        }
        return false;
    }
    public void addLive() {
        this.lives += 1;
    }
}
