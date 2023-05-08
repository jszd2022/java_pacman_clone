package gameCharacters;

import gameMechanics.Rules;
import gameMechanics.StaticData;
import gameTypes.Mode;
import gameTypes.Position;

public class Clyde extends Ghost implements GeneralGhost {
    public Clyde(Rules r, Position startingPoint, Mode gameMode) throws Exception {
        super(r, startingPoint, StaticData.clydeSpriteOffsetX, StaticData.clydeSpriteOffsetY,gameMode);
        this.scatterPosition = new Position(27,30);
        spawnWait = 100;
        spawnWaitStatic=spawnWait;
    }

    @Override
    public Position getTargetPosition(PacMan pacMan) {
        if (this.characterMode == Mode.CHASE) {
            if(this.getMatrixPosition().getDistanceTo(pacMan.getMatrixPosition()) < 8) {
                return this.scatterPosition;
            } else {
                return pacMan.getMatrixPosition();
            }
        } else if (this.characterMode == Mode.SCATTER) {
            return this.scatterPosition;
        }
        return null;
    }

}
