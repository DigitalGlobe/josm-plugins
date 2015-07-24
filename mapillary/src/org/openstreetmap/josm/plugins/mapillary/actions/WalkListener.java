package org.openstreetmap.josm.plugins.mapillary.actions;

/**
 * Implemented by those classes that need to listen to the creation of the walk
 * threads.
 *
 * @author nokutu
 *
 */
public interface WalkListener {

  /**
   * Called when a new walk thread is started.
   *
   * @param thread
   */
  public void walkStarted(WalkThread thread);
}