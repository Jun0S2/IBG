from django.db import models


# Create your models here.
# JPA의 엔티티 같은 개념


class AuthGroup(models.Model):
    name = models.CharField(unique=True, max_length=150)

    class Meta:
        managed = False
        db_table = 'auth_group'


class AuthGroupPermissions(models.Model):
    id = models.BigAutoField(primary_key=True)
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)
    permission = models.ForeignKey('AuthPermission', models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_group_permissions'
        unique_together = (('group', 'permission'),)


class AuthPermission(models.Model):
    name = models.CharField(max_length=255)
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING)
    codename = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'auth_permission'
        unique_together = (('content_type', 'codename'),)


class AuthUser(models.Model):
    password = models.CharField(max_length=128)
    last_login = models.DateTimeField(blank=True, null=True)
    is_superuser = models.IntegerField()
    username = models.CharField(unique=True, max_length=150)
    first_name = models.CharField(max_length=150)
    last_name = models.CharField(max_length=150)
    email = models.CharField(max_length=254)
    is_staff = models.IntegerField()
    is_active = models.IntegerField()
    date_joined = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'auth_user'


class AuthUserGroups(models.Model):
    id = models.BigAutoField(primary_key=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_groups'
        unique_together = (('user', 'group'),)


class AuthUserUserPermissions(models.Model):
    id = models.BigAutoField(primary_key=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    permission = models.ForeignKey(AuthPermission, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_user_permissions'
        unique_together = (('user', 'permission'),)


class Chat(models.Model):
    chat_no = models.AutoField(primary_key=True)
    deal_no = models.IntegerField()
    user_no = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'chat'


class Deal(models.Model):
    dael_no = models.AutoField(primary_key=True)
    deal_content = models.CharField(max_length=255, blank=True, null=True)
    deal_file_name = models.CharField(max_length=255, blank=True, null=True)
    deal_path = models.CharField(max_length=255, blank=True, null=True)
    deal_price = models.IntegerField()
    deal_reg = models.DateTimeField()
    deal_saved_name = models.CharField(max_length=255, blank=True, null=True)
    deal_status = models.TextField()  # This field type is a guess.
    deal_title = models.CharField(max_length=255, blank=True, null=True)
    game_no = models.ForeignKey('Game', models.DO_NOTHING, db_column='game_no', blank=True, null=True)
    user_no = models.ForeignKey('User', models.DO_NOTHING, db_column='user_no', blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'deal'


class DjangoAdminLog(models.Model):
    action_time = models.DateTimeField()
    object_id = models.TextField(blank=True, null=True)
    object_repr = models.CharField(max_length=200)
    action_flag = models.PositiveSmallIntegerField()
    change_message = models.TextField()
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING, blank=True, null=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'django_admin_log'


class DjangoContentType(models.Model):
    app_label = models.CharField(max_length=100)
    model = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'django_content_type'
        unique_together = (('app_label', 'model'),)


class DjangoMigrations(models.Model):
    id = models.BigAutoField(primary_key=True)
    app = models.CharField(max_length=255)
    name = models.CharField(max_length=255)
    applied = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_migrations'


class DjangoSession(models.Model):
    session_key = models.CharField(primary_key=True, max_length=40)
    session_data = models.TextField()
    expire_date = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_session'


class Game(models.Model):
    game_no = models.AutoField(primary_key=True)
    game_age = models.IntegerField()
    game_category = models.CharField(max_length=255, blank=True, null=True)
    game_desc = models.CharField(max_length=6000, blank=True, null=True)
    game_kor_desc = models.CharField(max_length=6000, blank=True, null=True)
    game_img = models.CharField(max_length=255, blank=True, null=True)
    game_max_player = models.IntegerField()
    game_max_time = models.IntegerField()
    game_min_player = models.IntegerField()
    game_min_time = models.IntegerField()
    game_name = models.CharField(unique=True, max_length=255, blank=True, null=True)
    game_kor_name = models.CharField(unique=True, max_length=255, blank=True, null=True)
    game_total_score = models.FloatField()
    game_weight = models.FloatField()
    game_year = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'game'


class Interest(models.Model):
    like_no = models.AutoField(primary_key=True)
    game_no = models.ForeignKey(Game, models.DO_NOTHING, db_column='game_no', blank=True, null=True)
    user_no = models.ForeignKey('User', models.DO_NOTHING, db_column='user_no', blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'interest'


class Log(models.Model):
    log_no = models.AutoField(primary_key=True)
    chat_no = models.IntegerField()
    log_content = models.IntegerField()
    log_reg = models.DateTimeField()
    user_no = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'log'


class Review(models.Model):
    review_no = models.AutoField(primary_key=True)
    review_content = models.CharField(max_length=255, blank=True, null=True)
    review_reg = models.DateTimeField()
    game_no = models.ForeignKey(Game, models.DO_NOTHING, db_column='game_no', blank=True, null=True)
    user_no = models.ForeignKey('User', models.DO_NOTHING, db_column='user_no', blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'review'


class Score(models.Model):
    score_no = models.AutoField(primary_key=True)
    score_rating = models.IntegerField()
    game_no = models.ForeignKey(Game, models.DO_NOTHING, db_column='game_no', blank=True, null=True)
    user_no = models.ForeignKey('User', models.DO_NOTHING, db_column='user_no', blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'score'


class User(models.Model):
    user_no = models.AutoField(primary_key=True)
    user_auth = models.CharField(max_length=20, blank=True, null=True)
    user_email = models.CharField(max_length=255)
    user_nick = models.CharField(max_length=255)
    user_pwd = models.CharField(max_length=255)

    class Meta:
        managed = False
        db_table = 'user'
        unique_together = (('user_email', 'user_nick'),)


class Recommend(models.Model):
    recommend_no = models.AutoField(primary_key=True)
    recommend_rating = models.FloatField()
    game_no = models.ForeignKey(Game, models.DO_NOTHING, db_column='game_no', blank=True, null=True)
    user_no = models.ForeignKey('User', models.DO_NOTHING, db_column='user_no', blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'recommend'
