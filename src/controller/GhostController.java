package controller;

import model.Arena;
import model.character.Blinky;
import model.character.Clyde;
import model.character.Ghost;
import model.character.Inky;
import model.character.Pinky;

/**
 * Kelas controller untuk Ghost.
 */
public class GhostController implements Runnable {

  private Ghost ghost;
  private Thread gThread;
  private String threadName;

  /**
   * Constructor GhostController
   */
  public GhostController(char type) {
    if (type == 'a') {
      ghost = new Blinky((int) Arena.getBlinkyInitPos().getX(),
          (int) Arena.getBlinkyInitPos().getY());
      this.threadName = "BlinkyThread";
    } else if (type == 'b') {
      ghost = new Pinky((int) Arena.getPinkyInitPos().getX(), (int) Arena.getPinkyInitPos().getY());
      this.threadName = "PinkyThread";
    } else if (type == 'c') {
      ghost = new Inky((int) Arena.getInkyInitPos().getX(), (int) Arena.getInkyInitPos().getY());
      this.threadName = "InkyThread";
    } else {
      ghost = new Clyde((int) Arena.getClydeInitPos().getX(), (int) Arena.getClydeInitPos().getY());
      this.threadName = "ClydeThread";
    }
  }

  public GhostController(int i, int j, char type, String threadName) {
    if (type == 'a') {
      ghost = new Blinky(i, j);
      this.threadName = threadName;
    } else if (type == 'b') {
      ghost = new Pinky(i, j);
      this.threadName = threadName;
    } else if (type == 'c') {
      ghost = new Inky(i, j);
      this.threadName = threadName;
    } else {
      ghost = new Clyde(i, j);
      this.threadName = threadName;
    }
  }

  /**
   * Fungsi menentukan pergerakan Ghost selanjutnya.
   *
   * @return Nilai integer yang menentukan arah gerak Ghost berdasarkan kondisi Ghost.
   */
  public int nextMovement() {
    if (ghost.isNormal()) {
      return ghost.moveTowardsPlayer();
    } else if (ghost.isVulnerable()) {
      return ghost.moveAwayFromPlayer();
    } else {
      return ghost.returnToBase();
    }
  }

  /**
   * Method untuk menggerakkan Ghost berdasarkan kondisi Ghost.
   */
  public void run() {
    try {
      while (true) {
        int movement = nextMovement();
        if (movement == 1) {
          ghost.moveUp();
          if (ghost instanceof Blinky) {
            Blinky.setBlinkyI(ghost.getI() - 1);
          }
        } else if (movement == 2) {
          ghost.moveRight();
          if (ghost instanceof Blinky) {
            Blinky.setBlinkyJ(ghost.getJ() + 1);
          }
        } else if (movement == 3) {
          ghost.moveDown();
          if (ghost instanceof Blinky) {
            Blinky.setBlinkyI(ghost.getI() + 1);
          }
        } else {
          ghost.moveLeft();
          if (ghost instanceof Blinky) {
            Blinky.setBlinkyJ(ghost.getJ() - 1);
          }
        }
        Thread.sleep(500);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void start() {
    if (gThread == null) {
      gThread = new Thread(this, threadName);
      gThread.start();
    }
  }

  public Ghost getGhost() {
    return ghost;
  }
}
