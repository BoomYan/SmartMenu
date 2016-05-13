//
//  ViewController.m
//  Menu
//
//  Created by 孙翔宇 on 16/4/8.
//  Copyright (c) 2016年 孙翔宇. All rights reserved.
//

#import "ViewController.h"
#import "SMAnnotation.h"
#import "MenuDetailsController.h"
#import "Venue.h"
#import "SMAnnotation.h"
#import "SMLocation.h"
#import "AFNetworking.h"
#import "MJExtension.h"
#import "MenuCell.h"
#import "MenuReviewsController.h"
#import <MapKit/MapKit.h>
#import <CoreLocation/CoreLocation.h>

@interface ViewController () <CLLocationManagerDelegate,MKMapViewDelegate>
@property (weak, nonatomic) IBOutlet MKMapView *mapView;
@property(nonatomic, strong) CLLocationManager *locationManager;
@property(nonatomic, strong) NSArray *venues;


@end

@implementation ViewController

- (CLLocationManager *)locationManager
{
    if (!_locationManager) {
        self.locationManager = [[CLLocationManager alloc] init];
    }
    return _locationManager;
}


- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.locationManager.delegate = self;
    [self.locationManager requestWhenInUseAuthorization];
    [self.locationManager requestAlwaysAuthorization];
    [self.locationManager startUpdatingLocation];
    
    self.mapView.showsUserLocation = YES;
    self.mapView.userTrackingMode = MKUserTrackingModeFollow;
    
    
}



-(void)locationManager:(CLLocationManager *)manager didUpdateLocations:(NSArray *)locations
{
    CLLocation *loc = (CLLocation *)[locations firstObject];
    MKCoordinateRegion region = MKCoordinateRegionMake(loc.coordinate, MKCoordinateSpanMake(0.1, 0.1));
    [self.mapView setRegion:region];
    [manager stopUpdatingLocation];
}


- (void)mapView:(MKMapView *)mapView didUpdateUserLocation:(MKUserLocation *)userLocation
{
    NSLog(@"%f", userLocation.coordinate.latitude);
    MKCoordinateRegion region = MKCoordinateRegionMake(userLocation.coordinate, MKCoordinateSpanMake(0.05, 0.05));
    [mapView setRegion:region animated:YES];
}

//- (void)setupLocation
//{
//    
//    if ([CLLocationManager locationServicesEnabled]) {
//        NSLog(@"开始定位");
//        self.locationManager.delegate = self;
//        
//        //ios8+以上要授权，并且在plist文件中添加NSLocationWhenInUseUsageDescription，NSLocationAlwaysUsageDescription，值可以为空
//        if ([[[UIDevice currentDevice] systemVersion] floatValue] >= 8.0) {//ios8+，不加这个则不会弹框
//            [self.locationManager requestWhenInUseAuthorization];//使用中授权
//            [self.locationManager requestAlwaysAuthorization];
//        }
//        [self.locationManager startUpdatingLocation];
//    }else{
//        NSLog(@"定位失败，请确定是否开启定位功能");
//    }
//    
//}

-(void)mapView:(MKMapView *)mapView regionDidChangeAnimated:(BOOL)animated
{
    AFHTTPSessionManager *mgr = [[AFHTTPSessionManager alloc] init];
    mgr.responseSerializer = [AFJSONResponseSerializer serializer];
    
//    NSString *url = @"https://api.foursquare.com/v2/venues/search?client_id=BJPNULORLI2RZ1LPYRXSUA5JAQQGLY1PWIYRV0AL1QVDHNSV&client_secret=DI2GIHPLKE0NHH2ARROY0EYB4NZJ3KX1G020T3UN2ESUMWJJ";
//    NSMutableDictionary *params = [NSMutableDictionary dictionary];
//    params[@"ll"] = [NSString stringWithFormat:@"%f,%f", mapView.region.center.latitude, mapView.region.center.longitude];
//    params[@"radius"] = @(10000);
//    //    params[@"categoryId"] = [NSString stringWithFormat:@"%@", @"4d4b7105d754a06374d81259"];
//    params[@"query"] = @"restaurant";
//    params[@"v"] = @"20140806";
//    params[@"m"] = @"foursquare";
    NSString *url = @"http://smartmenu-backend.us-east-1.elasticbeanstalk.com/getNearRestaurants";
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    params[@"location"] = [NSString stringWithFormat:@"%f,%f",mapView.region.center.latitude, mapView.region.center.longitude];
    
    [mgr GET:url parameters:params success:^(NSURLSessionDataTask *task, id responseObject) {
//        NSLog(@"%", responseObject[@"response"][@"venues"]);
//        NSLog(@"%@", responseObject);
        self.venues = [Venue objectArrayWithKeyValuesArray:responseObject[@"restaurants"]];
        
        for (Venue *venue in self.venues) {
            SMAnnotation *annotation = [[SMAnnotation alloc] init];
            annotation.venue = venue;
            NSArray *loc = [venue.location componentsSeparatedByString:@","];
            annotation.coordinate = CLLocationCoordinate2DMake([loc[0] floatValue], [loc[1] floatValue]);
            annotation.title = venue.name;
            
            if ([self.mapView.annotations containsObject:annotation]) {
                break;
            }
            [self.mapView addAnnotation:annotation];
        }

    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"%@", error);
    }];


}

- (MKAnnotationView *)mapView:(MKMapView *)mapView viewForAnnotation:(id<MKAnnotation>)annotation
{
    if (![annotation isKindOfClass:[SMAnnotation class]]) {
        return nil;
    }
    static NSString *ID = @"anno";
    MKAnnotationView *annotationView = [mapView dequeueReusableAnnotationViewWithIdentifier:ID];
    if (annotationView == nil) {
        annotationView = [[MKAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:ID];
        annotationView.canShowCallout = YES;
    }
    
    
    annotationView.annotation = annotation;
    annotationView.image = [UIImage imageNamed:@"ic_category_1"];
    
    
    
    UIButton *rightButton = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 80, 50)];
    rightButton.backgroundColor = [UIColor grayColor];
    [rightButton setTitle:@"Details" forState:UIControlStateNormal];
    annotationView.rightCalloutAccessoryView = rightButton;
    
    return annotationView;
    
}

-(void)mapView:(MKMapView *)mapView annotationView:(MKAnnotationView *)view calloutAccessoryControlTapped:(UIControl *)control
{
    
    
    [self performSegueWithIdentifier:@"menu" sender:(SMAnnotation *)view.annotation];
    
    
}


- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    
    UINavigationController *nav =(UINavigationController *)segue.destinationViewController;
    MenuDetailsController *menuDetailsController = (MenuDetailsController *)nav.topViewController;
    
    menuDetailsController.annotation = sender;
    
    
}




@end
