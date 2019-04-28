package cn.tedu.shoot;

import java.util.Arrays;
import java.util.Random;

public class Logic {
    int shootIndex = 0;
    int flyEnterIndex = 0;
    private Sky sky = new Sky();
    private Hero hero = new Hero();
    private FlyingObject[] enemies = {};
    private Bullet[] bullets = {};
    private int score = 0;

    public Logic() {
    }

    public FlyingObject nextOne() {
        Random rand = new Random();
        int type = rand.nextInt(20);
        if (type < 5) {
            return new Bee();
        } else if (type < 12) {
            return new Airplane();
        } else {
            return new BigAirplane();
        }
    }

    public void stepAction() {
        sky.step();
        for (FlyingObject enemy : enemies) {
            enemy.step();
        }
        for (Bullet bullet : bullets) {
            bullet.step();
        }
    }

    public void shootAction() {
        shootIndex++;
        if (shootIndex % 30 == 0) {
            Bullet[] bs = hero.shoot();
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
        }
    }

    public void bangAction() {
        for (Bullet b : bullets) {
            for (FlyingObject f : enemies) {
                if (b.isLife() && f.isLife() && b.hit(f)) {
                    b.goDead();
                    f.goDead();
                    if (f instanceof Enemy) {
                        Enemy e = (Enemy) f;
                        score += e.getScore();
                    }
                    if (f instanceof Award) {
                        Award a = (Award) f;
                        int type = a.getType();
                        switch (type) {
                            case Award.DOUBLE_FIRE -> hero.addDoubleFire();
                            case Award.LIFE -> hero.addLife();
                        }
                    }

                }
            }
        }
    }

    public void outOfBoundsAction() {
        int index = 0;
        FlyingObject[] enemyLives = new FlyingObject[enemies.length];
        for (FlyingObject e : enemies) {
            if (!e.outOfBounds() && !e.isRemove()) {
                enemyLives[index++] = e;
            }
        }
        enemies = Arrays.copyOf(enemyLives, index);

        index = 0;
        Bullet[] bulletLives = new Bullet[bullets.length];
        for (Bullet b : bullets) {
            if (!b.outOfBounds() && !b.isRemove()) {
                bulletLives[index++] = b;
            }
        }
        bullets = Arrays.copyOf(bulletLives, index);
    }

    public void hitAction() {
        for (FlyingObject f : enemies) {
            if (f.isLife() && hero.hit(f)) {
                f.goDead();
                hero.subtractLife();
                hero.clearDoubleFire();
                break;
            }
        }
    }

    public void restart() {
        hero = new Hero();
        FlyingObject[] enemies = {};
        Bullet[] bullets = {};
    }

    public void enterAction() {
        flyEnterIndex++;
        if (flyEnterIndex % 40 == 0) {
            FlyingObject obj = nextOne();
            enemies = Arrays.copyOf(enemies, enemies.length + 1);
            enemies[enemies.length - 1] = obj;
        }
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Sky getSky() {
        return sky;
    }

    public void setSky(Sky sky) {
        this.sky = sky;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public FlyingObject[] getEnemies() {
        return enemies;
    }

    public void setEnemies(FlyingObject[] enemies) {
        this.enemies = enemies;
    }

    public Bullet[] getBullets() {
        return bullets;
    }

    public void setBullets(Bullet[] bullets) {
        this.bullets = bullets;
    }

    public int getShootIndex() {
        return shootIndex;
    }

    public void setShootIndex(int shootIndex) {
        this.shootIndex = shootIndex;
    }

    public int getFlyEnterIndex() {
        return flyEnterIndex;
    }

    public void setFlyEnterIndex(int flyEnterIndex) {
        this.flyEnterIndex = flyEnterIndex;
    }
}
