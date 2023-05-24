package Patterns;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Observable {
	List<Observateur> observateurs;

	public Observable() {
		observateurs = new CopyOnWriteArrayList<>();
	}

	public void ajouteObservateur(Observateur o) {
		observateurs.add(o);
	}

	public void supprimeTousObservateurs() {
		observateurs.clear();
	}

	public void metAJour() {
		Iterator<Observateur> it;

		it = observateurs.iterator();
		while (it.hasNext()) {
			Observateur o = it.next();
			o.miseAJour();
		}
	}
}

