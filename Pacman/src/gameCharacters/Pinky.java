package gameCharacters;

import gameMechanics.Rules;
import gameMechanics.StaticData;
import gameTypes.Mode;
import gameTypes.Position;

public class Pinky extends Ghost {
    public Pinky(Rules r, Position startingPoint, Mode gameMode) throws Exception {
        super(r, startingPoint, StaticData.pinkySpriteOffsetX, StaticData.pinkySpriteOffsetY,gameMode);
        this.scatterPosition = new Position(2,2);
        spawnWait = 0;
        spawnWaitStatic=spawnWait;

    }

    @Override
    public Position getTargetPosition(PacMan pacMan) {
        if (this.characterMode == Mode.CHASE) {
            var tmp = new Position(pacMan.getMatrixPosition());
            tmp.addDirection(pacMan.getDirection(),4);
            return tmp;
        } else if (this.characterMode == Mode.SCATTER) {
            return this.scatterPosition;
        }
        return null;
    }
}
