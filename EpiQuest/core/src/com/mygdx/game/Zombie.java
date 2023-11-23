    package com.mygdx.game;

    import com.badlogic.gdx.graphics.Texture;


    public class Zombie extends Monster {

        public Zombie(float x, float y) {
            super(null, null, null, null, new Texture("Characters/zombie.png"),3,5); // Les paramètres seront définis dans la méthode init
            this.x = x;
            this.y = y;
        }
    }