package com.mygdx.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

public class CollisionUtils {
    public static boolean isCollidingWith(TiledMap map, String layer, float x, float y, float width, float height) {
        MapLayer collisionLayer = map.getLayers().get(layer);
        if (collisionLayer != null) {
            MapObjects objects = collisionLayer.getObjects();
            Rectangle playerRect = new Rectangle(x, y, width, height);
            for (MapObject object : objects) {
                if (object instanceof RectangleMapObject) {
                    RectangleMapObject rectObject = (RectangleMapObject) object;
                    Rectangle objectRect = rectObject.getRectangle();

                    if (playerRect.overlaps(objectRect)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
