package tag
class TodoTag extends Tag{
    Date deadline
    Boolean done
    TodoTag(){
        this.type='tag.todo'
    }
}
