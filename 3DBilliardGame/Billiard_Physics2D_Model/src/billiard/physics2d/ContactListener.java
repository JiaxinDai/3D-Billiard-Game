package billiard.physics2d;

import billiard.physics2d.Contact;

public interface ContactListener {

    public void onCollisionEnter(Contact contact);
    public void onCollision(Contact contact);
    public void onCollisionOut(Contact contact);
    
}
