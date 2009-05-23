class LiveTaggedController {
    // these will be injected by Griffon
    def model
    def view

    void mvcGroupInit(Map args) {
        /*model.searchViews.addAll([
          new SearchView(condition:{true},sortComparator:{a,b->0})  
        ])*/
        // this method is called after model and view are injected
    }

    /*
    def action = { evt = null ->
    }
    */
}
