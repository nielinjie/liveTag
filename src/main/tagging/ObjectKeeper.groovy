package tagging
interface ObjectKeeper{
	Object get(def id)
	List search(def filter)
	void clear()
	void put(Object obj)
	void remove(def id)
	void start()
	void close()
}
interface ObjectKeeperConfig{
	void config()
}