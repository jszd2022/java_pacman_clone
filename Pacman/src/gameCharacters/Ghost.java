package gameCharacters;

import gameMechanics.Rules;
import gameMechanics.StaticData;
import gameTypes.Direction;
import gameTypes.Mode;
import gameTypes.Position;
import gameTypes.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Ghost implements GeneralGhost {
    protected Sprite[] sprites;
    protected Position pixelPosition;
    protected Position matrixPosition;
    protected Position scatterPosition;
    protected Mode characterMode;
    protected int selectedSpriteSet;
    private boolean alive;
    protected Direction direction;
    protected boolean turnAround;
    protected boolean flashing;
    protected int spawnWait;
    protected int spawnWaitStatic;

    public Ghost(Rules r, Position startingPoint, int spriteOffsX, int spriteOffsY, Mode characterMode) throws Exception{
        selectedSpriteSet = 0;
        alive = true;
        this.characterMode = characterMode;

        this.sprites = new Sprite[12];

        BufferedImage[] tmp = new BufferedImage[4];
        BufferedImage buffer = ImageIO.read(
                new File("spriteImages/"+r.getTheme()+".png")
        );
        int spriteSize = StaticData.spriteSize;
        for(int j = 0; j < 4; j++) {
            for(int i = 0; i < 4; i++) {
                tmp[i] = buffer.getSubimage(
                        j * spriteSize + spriteOffsX,
                        i * spriteSize + spriteOffsY,
                        spriteSize, spriteSize
                );
            }
            this.sprites[j] = new Sprite(tmp);
        }
        for(int j = 0; j < 4; j++) {
            for(int i = 0; i < 4; i++) {
                tmp[i] = buffer.getSubimage(
                        j * spriteSize + StaticData.blueSpriteOffsetX,
                        i * spriteSize + StaticData.blueSpriteOffsetY,
                        spriteSize, spriteSize
                );
            }
            this.sprites[j+4] = new Sprite(tmp);
        }
        for(int j = 0; j < 4; j++) {
            this.sprites[j+8] = new Sprite(buffer.getSubimage(
                    spriteSize + StaticData.eatenSpriteOffsetX,
                    j * spriteSize + StaticData.eatenSpriteOffsetY,
                    spriteSize, spriteSize
            ));
        }

        this.pixelPosition = new Position(
                r.getCellDistance() * startingPoint.getX(),
                r.getCellDistance() * startingPoint.getY()
        );
        this.matrixPosition = new Position(
                startingPoint.getX(),
                startingPoint.getY()
        );
        turnAround = false;
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
        int mode = 0;
        if(this.characterMode == Mode.FRIEGHTENED)
            mode = 4;
        if(!this.isAlive())
            mode = 8;
        return sprites[selectedSpriteSet+mode];
    }

    @Override
    public void updateSpriteDirection() {
        this.selectedSpriteSet = direction.getId() ;
    }

    public int getSpawnWait() {
        return spawnWait;
    }

    public void setSpawnWait(int spawnWait) {
        this.spawnWait = spawnWait;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
        if (!alive)
            setFlashing(false);
    }

    @Override
    public void decrementLives() {
        setAlive(false);
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Position getTargetPosition(PacMan pacMan) { return null;}

    public Mode getCharacterMode() {
        return characterMode;
    }

    public void setCharacterMode(Mode characterM) {
        this.characterMode = characterM;
        setFlashing(false);

    }
    public void revive() {
        if(!this.isAlive())
            this.setCharacterMode(Mode.CHASE);
        setFlashing(false);
        this.setAlive(true);
    }
    public void setTurnAround(boolean turnAround) {
        this.turnAround = turnAround;
    }

    public boolean isTurnAroundAndReset() {
        if(turnAround) {
            turnAround = false;
            return true;
        }
        return false;
    }

    public boolean isFlashing() {
        return flashing;
    }

    public void setFlashing(boolean flashing) {
        this.flashing = flashing && this.characterMode == Mode.FRIEGHTENED;
    }

    public void resetWait() {
        spawnWait = spawnWaitStatic;
    }
}
