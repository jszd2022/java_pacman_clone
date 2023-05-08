package gameCharacters;

import gameMechanics.Rules;
import gameMechanics.StaticData;
import gameTypes.Mode;
import gameTypes.Position;

public class Inky extends Ghost {
    private Position blinkysPosition;

    public Inky(Rules r, Position startingPoint, Mode gameMode, Position blinkysPosition) throws Exception {
        super(r, startingPoint, StaticData.inkySpriteOffsetX, StaticData.inkySpriteOffsetY, gameMode);
        this.blinkysPosition = blinkysPosition;
        this.scatterPosition = new Position(2,30);
        this.spawnWait = 50;
        spawnWaitStatic=spawnWait;
    }

    @Override
    public Position getTargetPosition(PacMan pacMan) {
        var target = new Position(pacMan.getMatrixPosition());
        target.addDirection(pacMan.getDirection(),2);
        var newPosition = new Position(this.blinkysPosition);
        int x, y;
        x = target.getX();
        y = target.getY();
        newPosition.addToX(-x);
        newPosition.addToY(-y);

        newPosition.setX(-newPosition.getX());
        newPosition.setY(-newPosition.getY());

        newPosition.addToX(x);
        newPosition.addToY(y);

        return newPosition;
    }
}