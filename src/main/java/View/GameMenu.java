package View;

import Contoller.*;
import Enums.Message;
import Models.Game;
import Models.Tile.Improvement;
import Models.Unit.Unit;
import Models.Unit.UnitType;
import Models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMenu extends Menu{
	private static GameMenu instance;
	private GameController gameController;
	private UnitActionController unitController;
	private SelectController selectController;

	private GameMenu() {
		this.gameController = GameController.getInstance();
		this.unitController = UnitActionController.getInstance();
		this.selectController = SelectController.getInstance();
	}
	static GameMenu getInstance() {
		GameMenu.instance = new GameMenu();
		return GameMenu.instance;
	}




	@Override
	public Menu run() {
		while (true) {
			String command = getInput();
			if (command.equals("menu exit"))
				return MainMenu.getInstance();
			if (command.startsWith("menu enter")) {
				System.out.println("menu navigation is not possible");
				continue;
			}
			if (command.equals("menu show-current")) {
				System.out.println("Game Menu");
				continue;
			}
			CommandExtractor extractor = CommandExtractor.extractCommand(command);
			if (extractor == null || !command.startsWith("play game")) {
				System.out.println(Message.INVALID_COMMAND);
				continue;
			}
			System.out.println("debug: start game?");
			ArrayList<User> users = usernameToUser(extractor.args);
			gameController.startNewGame(users);
            startGame();

		}
	}

    private void startGame() {
		GameController.getInstance().startNewGame(User.getUserList());
		System.out.println("debug: game started");
		
        while (true) {
			// TODO: shit
			GameController.getInstance().getGame().getGameMap().printMap();
            String command = getInput();
			if (command.equals("EXIT"))
				break;
			taskHandler(command);
        }
    }

	private void taskHandler(String command) {
		GameMenuExtractor extractor = GameMenuExtractor.extractor(command);
		if (extractor == null) {
			System.out.println(Message.INVALID_COMMAND);
			return;
		}
		switch (extractor.getType()) {
			case INFO:
				String infoType = extractor.ARGS1.get("info type");
				String technology;
				System.out.println(InfoMenuController.getInstance().showInfoMenu(infoType));
				if (infoType.equals("RESEARCH")) {
					technology = getInput();
					System.out.println(InfoMenuController.getInstance().research(technology));
				}
				return;
			case SELECT_COMBAT_UNIT:
				System.out.println(
						selectController.selectUnit(
                                GameController.getInstance().getGame().getCurrentPlayer().getCivilization(),
                                extractor.ARGS2.get("x"), extractor.ARGS2.get("y"), true)
				);
				return;
			case SELECT_NONCOMBAT_UNIT:
				System.out.println(
						selectController.selectUnit(
                                GameController.getInstance().getGame().getCurrentPlayer().getCivilization(),
                                extractor.ARGS2.get("x"), extractor.ARGS2.get("y"), false)
						);
				return;
			case SELECT_CITY_BY_POSITION:
				System.out.println(
						selectController.selectCityByCoordination(
                                GameController.getInstance().getGame().getCurrentPlayer().getCivilization() ,
                                extractor.ARGS2.get("x"), extractor.ARGS2.get("y"))
				);
				return;

	//UNITS
			case UNIT_MOVE:
				System.out.println(
					MoveController.getInstance().moveUnit(extractor.ARGS2.get("x"), extractor.ARGS2.get("y"))
				);
				return;
			case UNIT_SLEEP:
				System.out.println(unitController.sleep());
				return;
			case UNIT_ALERT:
				System.out.println(unitController.alert());
				break;
			case UNIT_FORTIFY:
				System.out.println(unitController.fortify());
				return;
			case UNIT_GARRISON:
				System.out.println(unitController.garrison());
				return;
			case UNIT_SETUP_FOR_RANGED:
				System.out.println(
                        unitController.setupForRangedAttack(extractor.ARGS2.get("x"), extractor.ARGS2.get("y"))
                );
				return;
			case UNIT_ATTACK:
				System.out.println(
						unitController.attack(extractor.ARGS2.get("x"), extractor.ARGS2.get("y"))
				);
				return;
			case UNIT_FOUND_CITY:
				System.out.println(unitController.foundCity());
				return;
			case UNIT_CANCEL_MISSION:
				//System.out.println(unitController.cancelMissions());
				return;
			case UNIT_WAKE:
				System.out.println(unitController.wake());
				return;
			case UNIT_DELETE:
				System.out.println(unitController.delete());
				return;
			case UNIT_BUILD_ROAD:
				System.out.println(unitController.buildRoad());
				return;
			case UNIT_BUILD_IMPROVEMENT:
                Improvement improvement = Improvement.valueOf(extractor.ARGS1.get("improvement"));
                System.out.println(unitController.buildImprovement(improvement));
				return;
			case UNIT_REMOVE:
                if (extractor.ARGS1.get("removeType").equals("JUNGLE"))
                    System.out.println(unitController.removeJungle());
                else if (extractor.ARGS1.get("removeType").equals("ROUTE"))
                    System.out.println(unitController.removeRoad());
                else System.out.println(Message.INVALID_COMMAND);
                return;
			case UNIT_REPAIR:
                System.out.println(unitController.repair());
				return;
	//MAP
			case MAP_MOVE:
				//TODO:
				break;
			case MAP_SHOW_BY_POSITION:
				//TODO:
				break;
     //CITY
            case CITY_BANNER:
                //TODO:
                break;
            case CITY_PURCHASE_TILE:
                System.out.println(
                        CityLogicController.getInstance().buyTile(extractor.ARGS2.get("x"), extractor.ARGS2.get("y"))
                );
                break;
            case CITY_PURCHASE_UNIT:
                System.out.println(
                        UnitActionController.getInstance().buyUnit(
                                GameController.getInstance().getGame().getCurrentPlayer().getCivilization(),
                                UnitType.valueOf(extractor.ARGS1.get("unitType"))));
                break;
            case CITY_GET_OUTPUT:
                switch (extractor.ARGS1.get("outputType")) {
                    case "FOOD":
                        System.out.println(
                                CityLogicController.getInstance().getFoodOut(
                                        GameController.getInstance().getGame().getCurrentPlayer().getCivilization()));
                        return;
                    case "PRODUCT":
                        System.out.println(
                                CityLogicController.getInstance().getProductionOut(
                                        GameController.getInstance().getGame().getCurrentPlayer().getCivilization()));
                        return;
                    case "GOLD":
                        System.out.println(
                                CityLogicController.getInstance().getGoldOut(
                                        GameController.getInstance().getGame().getCurrentPlayer().getCivilization()));
                    case "SCIENCE":
                        System.out.println(
                                CityLogicController.getInstance().getScienceOut(
                                        GameController.getInstance().getGame().getCurrentPlayer().getCivilization()));
                        return;
                }

                break;
            case CITY_LOCK_CITIZEN:
                System.out.println(
                        CityLogicController.getInstance().assignCitizenToTile(extractor.ARGS2.get("x"),
                                extractor.ARGS2.get("y"))
                );
                break;
            case CITY_REMOVE_FROM_WORK:
                System.out.println(
                        CityLogicController.getInstance().removeCitizenFromWork(extractor.ARGS2.get("x"),
                                extractor.ARGS2.get("y"))
                );
                break;
            default:
                System.out.println(Message.INVALID_COMMAND);
                break;
		}
	}

}
